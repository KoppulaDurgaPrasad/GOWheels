import React, { useState } from "react";

import API from "./api";
import { useNavigate } from "react-router-dom";
import "./Bookings.css";

function Bookings({
  rentals = [],
  cars = [],
  setSelectedCar,
  setSelectedImage,
  role,
}) {
  const navigate = useNavigate();

  const [editingRentalId, setEditingRentalId] = useState(null);

  const [selectedStatus, setSelectedStatus] = useState("");

  const [showCustomerDetails, setShowCustomerDetails] = useState(null);

  const [selectedCustomer, setSelectedCustomer] = useState(null);

  const handleSaveStatus = async (rentalId) => {
    try {
      await API.put(`/api/admin/rentals/${rentalId}?status=${selectedStatus}`);

      window.location.reload();
    } catch (err) {}
  };

  return (
    <div className="bookingPage">
      {rentals.length === 0 ? (
        <div className="emptyBookingBox">
          <h2>No Active Bookings</h2>

          <p>Currently there are no rented cars available.</p>
        </div>
      ) : (
        <div className="bookingGrid">
          {rentals.map((rental) => (
            <div key={rental.rentalId}>
              <div className="bookingImageContainer">
                <img
                  src={rental.imageUrl || "https://via.placeholder.com/400x220"}
                  alt="Car"
                  className="bookingImage"
                />
              </div>

              <div className="carInfo">
                <h2 className="bookingCarName">
                  {rental.carBrand} {rental.carModel}
                </h2>

                <div className="bookingTop">
                  <div className="approvalBox">
                    <span className="approvalLabel">Approval :</span>

                    {editingRentalId === rental.rentalId ? (
                      <select
                        className="statusSelect"
                        value={selectedStatus}
                        onChange={(e) => setSelectedStatus(e.target.value)}
                      >
                        <option value="PENDING">Pending</option>

                        <option value="APPROVED">Approved</option>

                        <option value="COMPLETED">Completed</option>

                        <option value="CANCELLED">Cancelled</option>
                      </select>
                    ) : (
                      <p
                        className={
                          rental.status === "APPROVED"
                            ? "bookingStatus approvedStatus"
                            : "bookingStatus pendingStatus"
                        }
                      >
                        {rental.status}
                      </p>
                    )}
                  </div>

                  <div className="bookingBtnGroup">
                    <button
                      className="viewBookingBtn"
                      onClick={() => {
                        const fullCar = cars.find(
                          (car) => car.id === rental.carId,
                        );

                        if (fullCar) {
                          setSelectedCar(fullCar);

                          setSelectedImage(fullCar.images?.[0]?.imageUrl);
                        }
                      }}
                    >
                      View
                    </button>

                    {role === "ADMIN" &&
                      (editingRentalId === rental.rentalId ? (
                        <button
                          className="saveBookingBtn"
                          onClick={() => handleSaveStatus(rental.rentalId)}
                        >
                          Save
                        </button>
                      ) : (
                        <button
                          className="editBookingBtn"
                          onClick={() => {
                            setEditingRentalId(rental.rentalId);

                            setSelectedStatus(rental.status);
                          }}
                        >
                          Edit
                        </button>
                      ))}
                  </div>
                </div>

                <div className="bookingDetails">
                  <div className="bookingRow">
                    <span>Start Date</span>

                    <p>{rental.startDate}</p>
                  </div>

                  <div className="bookingRow">
                    <span>End Date</span>

                    <p>{rental.endDate}</p>
                  </div>

                  <div className="bookingRow">
                    <span>Total Amount</span>

                    <p>₹{rental.totalAmount}</p>
                  </div>

                  {role === "ADMIN" && (
                    <button
                      className="customerDetailsBtn"
                      onClick={() => setSelectedCustomer(rental)}
                    >
                      {showCustomerDetails === rental.rentalId
                        ? "Hide Customer Details"
                        : "Customer Details"}
                    </button>
                  )}
                  {selectedCustomer && (
                    <div className="customerModalOverlay">
                      <div className="customerModal">
                        <button
                          className="closeCustomerModal"
                          onClick={() => setSelectedCustomer(null)}
                        >
                          ✕
                        </button>

                        <div className="customerModalHeader">
                          <img
                            src={
                              selectedCustomer.gender === "FEMALE"
                                ? "https://cdn-icons-png.flaticon.com/512/6997/6997662.png"
                                : "https://cdn-icons-png.flaticon.com/512/4140/4140048.png"
                            }
                            alt="customer"
                            className="customerProfileImage"
                          />

                          <div>
                            <h1>
                              {selectedCustomer.customerFirstName}{" "}
                              {selectedCustomer.customerLastName}
                            </h1>

                            <p>Customer Profile</p>
                          </div>
                        </div>

                        <div className="customerModalGrid">
                          <div className="customerInfoCard">
                            <span>Email</span>

                            <p>{selectedCustomer.customerEmail}</p>
                          </div>

                          <div className="customerInfoCard">
                            <span>Phone</span>

                            <p>{selectedCustomer.customerPhone}</p>
                          </div>

                          <div className="customerInfoCard">
                            <span>License</span>

                            <p>{selectedCustomer.licenseNumber}</p>
                          </div>

                          <div className="customerInfoCard">
                            <span>Identity Proof Type</span>

                            <p>{selectedCustomer.identityProofType}</p>
                          </div>

                          <div className="customerInfoCard">
                            <span>Identity Proof Number</span>

                            <p>{selectedCustomer.identityProofNumber}</p>
                          </div>
                        </div>

                        {selectedCustomer.documentUrl && (
                          <a
                            href={selectedCustomer.documentUrl}
                            target="_blank"
                            rel="noreferrer"
                            className="viewDocumentBtn"
                          >
                            View Uploaded Document
                          </a>
                        )}
                      </div>
                    </div>
                  )}
                </div>

                <div className="bookingMessage">
                  {rental.status === "PENDING"
                    ? "Your booking is under review. Admin will verify your details shortly."
                    : rental.status === "APPROVED"
                      ? "Your booking has been approved successfully. Please collect the car from the Store"
                      : rental.status === "COMPLETED"
                        ? "Your rental has been completed successfully."
                        : "Booking was cancelled."}
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default Bookings;
