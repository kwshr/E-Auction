package com.eauction.sellerservice;

import org.springframework.stereotype.Service;
import com.common.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

@Service
public class SellerImpl implements Seller {

    private final DatabaseConnection databaseConnection;

    public SellerImpl(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    @Override
    public SellerQueryResult addSellItems(Item item) {
        String itemName = item.getItemName();
        String itemDescription = item.getItemDescription();
        String auctionType = item.getAuctionType();
        long price = item.getPrice();
        int shippingTime = item.getShippingTime();
        double shippingCost = item.getShippingCost();
        double expeditedShippingCost = item.getExpeditedShippingCost();
        double finalShippingCost = item.getFinalShippingCost();
        int fixedTimeLimit = item.getFixedTimeLimit();
        long dutchReservedPrice = item.getDutchReservedPrice();
        long dutchDecrementAmount = item.getDutchDecrementAmount();
        int dutchDecrementTimeInterval = item.getDutchDecrementTimeInterval();
        int sellerId = item.getSellerId();
        
        try (Connection connection = databaseConnection.connect()) {
            String query = "INSERT INTO Items " +
                    "(ItemName, ItemDescription, AuctionType, Price, ShippingTime, ShippingCost, " +
                    "ExpeditedShippingCost, FinalShippingCost, FixedTimeLimit, DutchReservedPrice, " +
                    "DutchDecrementAmount, DutchDecrementTimeInterval, SellerID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, itemName);
                preparedStatement.setString(2, itemDescription);
                preparedStatement.setString(3, auctionType);
                preparedStatement.setLong(4, price);
                preparedStatement.setInt(5, shippingTime);
                preparedStatement.setDouble(6, shippingCost);
                preparedStatement.setDouble(7, expeditedShippingCost);
                preparedStatement.setDouble(8, finalShippingCost);
                preparedStatement.setInt(9, fixedTimeLimit);
                preparedStatement.setLong(10, dutchReservedPrice);
                preparedStatement.setLong(11, dutchDecrementAmount);
                preparedStatement.setInt(12, dutchDecrementTimeInterval);
                preparedStatement.setInt(13, sellerId);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    return new SellerQueryResult(SellerServiceQueryStatus.ERROR, "Failed to add item: " + itemName);
                } else {
                    return new SellerQueryResult(SellerServiceQueryStatus.SUCCESS, "Item added successfully: " + itemName);
                }
            }
        } catch (Exception e) {
            return new SellerQueryResult(SellerServiceQueryStatus.ERROR, "Failed to add item: " + e.getMessage());
        }
    }

    @Override
    public SellerQueryResult updateDutchAuctionprice(int itemId, double newPrice) {
        try (Connection connection = databaseConnection.connect()) {
            String query = "UPDATE Auctions SET CurrentPrice = ? WHERE ItemID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setDouble(1, newPrice);
                preparedStatement.setInt(2, itemId);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    return new SellerQueryResult(SellerServiceQueryStatus.NOT_FOUND, "Item not found");
                } else {
                    return new SellerQueryResult(SellerServiceQueryStatus.SUCCESS, "Dutch auction price updated successfully");
                }
            }
        } catch (Exception e) {
            return new SellerQueryResult(SellerServiceQueryStatus.ERROR, "Failed to update Dutch auction price: " + e.getMessage());
        }
    }
}