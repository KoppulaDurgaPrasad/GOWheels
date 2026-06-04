import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Sidebar from "./Sidebar";
import Cars from "./Cars";
import Profile from "./Profile";
import Bookings from "./Bookings";
import Footer from "./Footer";
import API from "./api";

import "./CustomerDashboard.css";

function CustomerDashboard() {
  const navigate = useNavigate();

  const [activeSection, setActiveSection] = useState("profile");

  const [showFilters, setShowFilters] = useState(false);

  const [search, setSearch] = useState("");

  const [cars, setCars] = useState([]);

  const [rentals, setRentals] = useState([]);

  const [filteredCars, setFilteredCars] = useState([]);

  const [selectedCar, setSelectedCar] = useState(null);

  const [selectedImage, setSelectedImage] = useState("");

  const [showBookingModal, setShowBookingModal] = useState(false);

  const [bookingCar, setBookingCar] = useState(null);

  const [bookingData, setBookingData] = useState({
    startDate: "",
    endDate: "",
  });

  const [bookingSuccess, setBookingSuccess] = useState("");
  const [customerData, setCustomerData] = useState(null);

  const [selectedCategories, setSelectedCategories] = useState([]);

  const [selectedFuels, setSelectedFuels] = useState([]);

  const [selectedPriceRanges, setSelectedPriceRanges] = useState([]);

  const [currentPage, setCurrentPage] = useState(1);

  const carsPerPage = 8;

  useEffect(() => {
    fetchCustomerProfile();
  }, []);

  useEffect(() => {
    if (activeSection === "cars" && cars.length === 0) {
      fetchCars();
    }
  }, [activeSection]);

  useEffect(() => {
    if (activeSection === "bookings" && rentals.length === 0) {
      fetchRentals();
    }
  }, [activeSection]);

  const fetchCars = async () => {
    try {
      const res = await API.get("/api/cars");
      setCars(res.data);

      setFilteredCars(res.data);
    } catch (err) {}
  };
  const fetchRentals = async () => {
    try {
      const res = await API.get("/api/customer/rentals");

      setRentals(res.data);
    } catch (err) {}
  };

  const fetchCustomerProfile = async () => {
    try {
      const res = await API.get("/api/customer/profile");

      setCustomerData(res.data);
    } catch (err) {}
  };

  const handleSearch = () => {
    const filtered = cars.filter((car) => {
      const text = `${car.carBrand} ${car.carModel}`.toLowerCase();

      return text.includes(search.toLowerCase());
    });

    setFilteredCars(filtered);
  };

  const toggleCategory = (category) => {
    setSelectedCategories((prev) =>
      prev.includes(category)
        ? prev.filter((item) => item !== category)
        : [...prev, category],
    );
  };

  const toggleFuel = (fuel) => {
    setSelectedFuels((prev) =>
      prev.includes(fuel)
        ? prev.filter((item) => item !== fuel)
        : [...prev, fuel],
    );
  };

  const togglePriceRange = (range) => {
    setSelectedPriceRanges((prev) =>
      prev.includes(range)
        ? prev.filter((item) => item !== range)
        : [...prev, range],
    );
  };

  const applyFilters = () => {
    let updated = [...cars];

    if (selectedCategories.length > 0) {
      updated = updated.filter((car) =>
        selectedCategories.includes(car.category),
      );
    }

    if (selectedFuels.length > 0) {
      updated = updated.filter((car) => selectedFuels.includes(car.fuelType));
    }

    if (selectedPriceRanges.length > 0) {
      updated = updated.filter((car) => {
        const price = Number(car.pricePerDay);

        return selectedPriceRanges.some((range) => {
          switch (range) {
            case "UNDER_2000":
              return price < 2000;

            case "2000_5000":
              return price >= 2000 && price <= 5000;

            case "5000_10000":
              return price >= 5000 && price <= 10000;

            case "ABOVE_10000":
              return price > 10000;

            default:
              return false;
          }
        });
      });
    }

    setFilteredCars(updated);

    setCurrentPage(1);

    setShowFilters(false);
  };

  const clearFilters = () => {
    setSelectedCategories([]);

    setSelectedFuels([]);

    setSelectedPriceRanges([]);

    setFilteredCars(cars);
  };

  const calculateDays = () => {
    if (!bookingData.startDate || !bookingData.endDate) {
      return 0;
    }

    const start = new Date(bookingData.startDate);

    const end = new Date(bookingData.endDate);

    const diffTime = end - start;

    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)) + 1;

    return diffDays > 0 ? diffDays : 0;
  };

  const totalDays = calculateDays();

  const totalPrice = totalDays * (bookingCar?.pricePerDay || 0);

  const indexOfLastCar = currentPage * carsPerPage;

  const indexOfFirstCar = indexOfLastCar - carsPerPage;

  const currentCars = filteredCars.slice(indexOfFirstCar, indexOfLastCar);

  const totalPages = Math.ceil(filteredCars.length / carsPerPage);

  const handleBookCar = async () => {
    try {
      const response = await API.post("/api/customer/rent", {
        carId: bookingCar.id,
        startDate: bookingData.startDate,
        endDate: bookingData.endDate,
      });

      if (response.status !== 200) {
        return;
      }

      setBookingSuccess(
        "You successfully booked the car now. Just for confirmation my admin will make a call to you and then come and collect the car from the garage. Thank you.",
      );

      fetchCars();
    } catch (err) {
      setBookingSuccess("Booking Failed");
    }
  };

  const logout = () => {
    localStorage.removeItem("token");

    navigate("/");
  };

  return (
    <>
      <div className="customerDashboard">
        <Sidebar
          activeSection={activeSection}
          setActiveSection={setActiveSection}
          logout={logout}
          adminData={customerData}
        />

        <div className="customerContent">
          {activeSection === "cars" && (
            <div className="customerSection">
              <Cars
                role="CUSTOMER"
                hideAddCar={true}
                filteredCars={currentCars}
                totalCars={filteredCars.length}
                search={search}
                setSearch={setSearch}
                handleSearch={handleSearch}
                setShowFilters={setShowFilters}
                setSelectedCar={setSelectedCar}
                setSelectedImage={setSelectedImage}
                setShowBookingModal={setShowBookingModal}
                setBookingCar={setBookingCar}
              />
            </div>
          )}

          {activeSection === "cars" && totalPages > 1 && (
            <div className="pagination">
              <button
                disabled={currentPage === 1}
                onClick={() => setCurrentPage(currentPage - 1)}
              >
                Prev
              </button>

              <span>
                Page {currentPage} of {totalPages}
              </span>

              <button
                disabled={currentPage === totalPages}
                onClick={() => setCurrentPage(currentPage + 1)}
              >
                Next
              </button>
            </div>
          )}

          {activeSection === "profile" && (
            <div className="customerSection">
              <Profile
                user={customerData}
                role="CUSTOMER"
                refreshProfile={fetchCustomerProfile}
              />
            </div>
          )}

          {activeSection === "bookings" && (
            <div className="customerSection">
              <Bookings
                rentals={rentals}
                cars={cars}
                role="CUSTOMER"
                setSelectedCar={setSelectedCar}
                setSelectedImage={setSelectedImage}
              />
            </div>
          )}
        </div>

        {showFilters && (
          <div className="filterOverlay" onClick={() => setShowFilters(false)}>
            <div className="filterPanel" onClick={(e) => e.stopPropagation()}>
              <div className="filterHeader">
                <h2>Filters</h2>

                <button onClick={() => setShowFilters(false)}>✖</button>
              </div>

              <div className="filterSection">
                <h3>Class</h3>

                {[
                  { label: "Economy", value: "ECONOMY" },
                  { label: "Sedan", value: "SEDAN" },
                  { label: "SUV", value: "SUV" },
                  { label: "Luxury", value: "LUXURY" },
                  { label: "Sports", value: "SPORTS" },
                  { label: "VAN/MPV", value: "VAN_MPV" },
                ].map((item) => (
                  <label key={item.value} className="checkboxItem">
                    <input
                      type="checkbox"
                      checked={selectedCategories.includes(item.value)}
                      onChange={() => toggleCategory(item.value)}
                    />

                    {item.label}
                  </label>
                ))}
              </div>

              <div className="filterSection">
                <h3>Fuel Type</h3>

                {["PETROL", "DIESEL", "ELECTRIC"].map((item) => (
                  <label key={item} className="checkboxItem">
                    <input
                      type="checkbox"
                      checked={selectedFuels.includes(item)}
                      onChange={() => toggleFuel(item)}
                    />

                    {item}
                  </label>
                ))}
              </div>

              <div className="filterSection">
                <h3>Price Range</h3>

                {[
                  {
                    label: "Under ₹2000",
                    value: "UNDER_2000",
                  },

                  {
                    label: "₹2000 - ₹5000",
                    value: "2000_5000",
                  },

                  {
                    label: "₹5000 - ₹10000",
                    value: "5000_10000",
                  },

                  {
                    label: "Above ₹10000",
                    value: "ABOVE_10000",
                  },
                ].map((item) => (
                  <label key={item.value} className="checkboxItem">
                    <input
                      type="checkbox"
                      checked={selectedPriceRanges.includes(item.value)}
                      onChange={() => togglePriceRange(item.value)}
                    />

                    {item.label}
                  </label>
                ))}
              </div>

              <div className="filterActions">
                <button className="clearFilterBtn" onClick={clearFilters}>
                  Clear
                </button>

                <button className="applyFilterBtn" onClick={applyFilters}>
                  Apply
                </button>
              </div>
            </div>
          </div>
        )}

        {showBookingModal && bookingCar && (
          <div
            className="bookingOverlay"
            onClick={() => {
              setShowBookingModal(false);

              setBookingSuccess("");

              setBookingData({
                startDate: "",
                endDate: "",
              });
            }}
          >
            <div className="bookingModal" onClick={(e) => e.stopPropagation()}>
              <h2>Book Car</h2>

              <img
                src={bookingCar.images?.[0]?.imageUrl}
                alt=""
                className="bookingImage"
              />

              <h3>
                {bookingCar.carBrand} {bookingCar.carModel}
              </h3>

              <p className="bookingPrice">
                ₹{bookingCar.pricePerDay}
                /day
              </p>

              <div className="bookingSummary">
                <div>
                  <span>Total Days:</span>

                  <h4>{totalDays}</h4>
                </div>

                <div>
                  <span>Total Price:</span>

                  <h3>₹{totalPrice}</h3>
                </div>
              </div>

              <div className="bookingInputs">
                <input
                  type="date"
                  min={new Date().toISOString().split("T")[0]}
                  value={bookingData.startDate}
                  onChange={(e) =>
                    setBookingData({
                      ...bookingData,
                      startDate: e.target.value,
                    })
                  }
                />

                <input
                  type="date"
                  min={
                    bookingData.startDate ||
                    new Date().toISOString().split("T")[0]
                  }
                  value={bookingData.endDate}
                  onChange={(e) =>
                    setBookingData({
                      ...bookingData,
                      endDate: e.target.value,
                    })
                  }
                />
              </div>
              {bookingSuccess && (
                <div className="bookingSuccessBox">{bookingSuccess}</div>
              )}

              <div className="bookingActions">
                <button
                  className="cancelBookingBtn"
                  onClick={() => {
                    setShowBookingModal(false);

                    setBookingSuccess("");

                    setBookingData({
                      startDate: "",
                      endDate: "",
                    });
                  }}
                >
                  Cancel
                </button>

                <button
                  className="confirmBookingBtn"
                  onClick={handleBookCar}
                  disabled={!!bookingSuccess}
                >
                  Confirm Booking
                </button>
              </div>
            </div>
          </div>
        )}

        {selectedCar && (
          <div className="detailsOverlay" onClick={() => setSelectedCar(null)}>
            <div className="detailsModal" onClick={(e) => e.stopPropagation()}>
              <button className="closeBtn" onClick={() => setSelectedCar(null)}>
                ✖
              </button>

              <div className="detailsContent">
                <div className="detailsLeft">
                  <div className="mainImageWrapper">
                    <img
                      src={selectedImage || selectedCar.images?.[0]?.imageUrl}
                      alt=""
                      className="mainDetailsImage"
                    />
                  </div>

                  <div className="thumbnailRow">
                    {selectedCar.images?.map((img, index) => (
                      <img
                        key={index}
                        src={img.imageUrl}
                        alt=""
                        className={
                          selectedImage === img.imageUrl
                            ? "thumbnailImage activeThumb"
                            : "thumbnailImage"
                        }
                        onClick={() => setSelectedImage(img.imageUrl)}
                      />
                    ))}
                  </div>
                </div>

                <div className="detailsRight">
                  <h1>
                    {selectedCar.carBrand} {selectedCar.carModel}
                  </h1>

                  <div className="detailsPrice">
                    ₹{selectedCar.pricePerDay}
                    <span>/day</span>
                  </div>

                  <div
                    className={
                      selectedCar.carStatus === "AVAILABLE"
                        ? "statusText availableText"
                        : selectedCar.carStatus === "RENTED"
                          ? "statusText rentedText"
                          : "statusText maintenanceText"
                    }
                  >
                    {selectedCar.carStatus}
                  </div>

                  <div className="detailsGrid">
                    <div className="detailCard">
                      <span>Fuel</span>

                      <h4>{selectedCar.fuelType}</h4>
                    </div>

                    <div className="detailCard">
                      <span>Transmission</span>

                      <h4>{selectedCar.transmission}</h4>
                    </div>

                    <div className="detailCard">
                      <span>Category</span>

                      <h4>
                        {selectedCar.category === "VAN_MPV"
                          ? "VAN/MPV"
                          : selectedCar.category}
                      </h4>
                    </div>

                    <div className="detailCard">
                      <span>Seats</span>
                      <h4>{selectedCar.seats}</h4>
                    </div>

                    <div className="detailCard">
                      <span>Year</span>

                      <h4>{selectedCar.manufacturedYear}</h4>
                    </div>
                  </div>

                  <div className="descriptionBox">
                    <h3>Description</h3>

                    <p>{selectedCar.description}</p>
                  </div>

                  {selectedCar.carStatus === "AVAILABLE" && (
                    <button
                      className="bookNowBigBtn"
                      onClick={() => {
                        setBookingCar(selectedCar);

                        setShowBookingModal(true);
                      }}
                    >
                      Book Now
                    </button>
                  )}
                </div>
              </div>
            </div>
          </div>
        )}
      </div>
      <Footer />
    </>
  );
}
export default CustomerDashboard;
