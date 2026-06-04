import React, { useEffect, useState } from "react";
import "./AdminDashboard.css";
import API from "./api";
import { toast } from "react-toastify";
import Sidebar from "./Sidebar";
import Profile from "./Profile";
import Bookings from "./Bookings";
import Cars from "./Cars";
import Footer from "./Footer";

function AdminDashboard() {
  const [cars, setCars] = useState([]);

  const [filteredCars, setFilteredCars] = useState([]);

  const [search, setSearch] = useState("");

  const [showFilters, setShowFilters] = useState(false);

  const [showModal, setShowModal] = useState(false);

  const [editingCar, setEditingCar] = useState(null);

  const [selectedCar, setSelectedCar] = useState(null);

  const [selectedImage, setSelectedImage] = useState("");

  const [images, setImages] = useState([]);

  const [activeSection, setActiveSection] = useState("cars");

  const [adminData, setAdminData] = useState(null);

  const [rentals, setRentals] = useState([]);

  const [selectedCategories, setSelectedCategories] = useState([]);

  const [selectedFuels, setSelectedFuels] = useState([]);

  const [selectedStatuses, setSelectedStatuses] = useState([]);

  const [selectedPriceRanges, setSelectedPriceRanges] = useState([]);

  const [currentPage, setCurrentPage] = useState(1);

  const carsPerPage = 8;

  const [carForm, setCarForm] = useState({
    carBrand: "",
    carModel: "",
    pricePerDay: "",

    manufacturedYear: "",
    seats: "",

    fuelType: "PETROL",
    transmission: "MANUAL",
    category: "SUV",
    carStatus: "AVAILABLE",
    description: "",
  });

  const [editingRentalId, setEditingRentalId] = React.useState(null);

  useEffect(() => {
    fetchCars();
    fetchRentals();

    fetchAdminProfile();
  }, []);

  const fetchCars = async () => {
    try {
      const res = await API.get("/api/cars");

      setCars(res.data);

      setFilteredCars(res.data);
    } catch (err) {
      toast.error("Failed to fetch cars");
    }
  };

  const fetchRentals = async () => {
    try {
      const res = await API.get("/api/admin/rentals");

      setRentals(res.data);
    } catch (err) {
      toast.error("Failed to fetch rentals");
    }
  };

  const fetchAdminProfile = async () => {
    try {
      const res = await API.get("/api/admin/profile");

      setAdminData(res.data);
    } catch (err) {
      toast.error("Failed to load profile");
    }
  };

  const handleSearch = () => {
    const filtered = cars.filter((car) => {
      const text = `${car.carBrand} ${car.carModel}`.toLowerCase();

      return text.includes(search.toLowerCase());
    });

    setFilteredCars(filtered);
    setCurrentPage(1);
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

  const toggleStatus = (status) => {
    setSelectedStatuses((prev) =>
      prev.includes(status)
        ? prev.filter((item) => item !== status)
        : [...prev, status],
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

    if (selectedStatuses.length > 0) {
      updated = updated.filter((car) =>
        selectedStatuses.includes(car.carStatus),
      );
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
    setSelectedStatuses([]);

    setSelectedPriceRanges([]);

    setFilteredCars(cars);
    setCurrentPage(1);
  };

  const openAddModal = () => {
    setEditingCar(null);

    setImages([]);

    setCarForm({
      carBrand: "",
      carModel: "",
      pricePerDay: "",

      manufacturedYear: "",
      seats: "",

      fuelType: "PETROL",
      transmission: "MANUAL",
      category: "SUV",
      carStatus: "AVAILABLE",
      description: "",
    });

    setShowModal(true);
  };

  const openEditModal = (car) => {
    setEditingCar(car);

    setCarForm({
      carBrand: car.carBrand || "",

      carModel: car.carModel || "",

      pricePerDay: car.pricePerDay || "",

      manufacturedYear: car.manufacturedYear || "",

      seats: car.seats || "",

      fuelType: car.fuelType || "PETROL",

      transmission: car.transmission || "MANUAL",

      category: car.category || "SUV",

      carStatus: car.carStatus || "AVAILABLE",

      description: car.description || "",
    });

    setShowModal(true);
  };

  const handleCarChange = (e) => {
    setCarForm({
      ...carForm,

      [e.target.name]: e.target.value,
    });
  };

  const handleImageChange = (e) => {
    setImages([...e.target.files]);
  };

  const handleSaveCar = async () => {
    try {
      const formData = new FormData();

      formData.append(
        "car",
        new Blob([JSON.stringify(carForm)], {
          type: "application/json",
        }),
      );

      images.forEach((img) => {
        formData.append("images", img);
      });

      if (!editingCar) {
        await API.post("/api/admin/cars", formData);

        toast.success("Car added successfully");
      } else {
        await API.put(`/api/admin/cars/${editingCar.id}`, formData);

        toast.success("Car updated successfully");
      }

      setShowModal(false);

      setEditingCar(null);

      setImages([]);

      fetchCars();
    } catch (err) {
      toast.error("Failed to save car");
    }
  };

  const deleteCar = async (id) => {
    if (
      !toast.info("Deleting car...", {
        autoClose: 1000,
      })
    )
      return;

    try {
      await API.delete(`/api/admin/cars/${id}`);

      toast.success("🚗 Car deleted successfully");

      fetchCars();
    } catch (err) {
      toast.error("❌ Failed to delete car");
    }
  };

  const logout = () => {
    localStorage.removeItem("token");

    window.location.href = "/";
  };

  const indexOfLastCar = currentPage * carsPerPage;

  const indexOfFirstCar = indexOfLastCar - carsPerPage;

  const currentCars = filteredCars.slice(indexOfFirstCar, indexOfLastCar);

  const totalPages = Math.ceil(filteredCars.length / carsPerPage);

  return (
    <>
      <div className="adminPage">
        <Sidebar
          activeSection={activeSection}
          setActiveSection={setActiveSection}
          logout={logout}
          adminData={adminData}
          role="ADMIN"
        />

        <div className="mainContent">
          {activeSection === "profile" && (
            <Profile role="ADMIN" user={adminData} />
          )}

          {activeSection === "bookings" && (
            <Bookings
              rentals={rentals}
              cars={cars}
              role="ADMIN"
              setSelectedCar={setSelectedCar}
              setSelectedImage={setSelectedImage}
            />
          )}
          {activeSection === "cars" && (
            <Cars
              filteredCars={currentCars}
              totalCars={filteredCars.length}
              search={search}
              setSearch={setSearch}
              handleSearch={handleSearch}
              setShowFilters={setShowFilters}
              openAddModal={openAddModal}
              setSelectedCar={setSelectedCar}
              setSelectedImage={setSelectedImage}
              openEditModal={openEditModal}
              deleteCar={deleteCar}
              role="ADMIN"
            />
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
                <h3>Status</h3>

                {["AVAILABLE", "RENTED", "MAINTENANCE"].map((item) => (
                  <label key={item} className="checkboxItem">
                    <input
                      type="checkbox"
                      checked={selectedStatuses.includes(item)}
                      onChange={() => toggleStatus(item)}
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

        {showModal && (
          <div className="modalOverlay" onClick={() => setShowModal(false)}>
            <div className="adminModal" onClick={(e) => e.stopPropagation()}>
              <button
                className="modalCloseBtn"
                onClick={() => setShowModal(false)}
              >
                ✖
              </button>

              <h2>{editingCar ? "Edit Car" : "Add Car"}</h2>

              <div className="carFormGrid">
                <input
                  type="text"
                  name="carBrand"
                  placeholder="Car Brand"
                  value={carForm.carBrand}
                  onChange={handleCarChange}
                />

                <input
                  type="text"
                  name="carModel"
                  placeholder="Car Model"
                  value={carForm.carModel}
                  onChange={handleCarChange}
                />

                <input
                  type="number"
                  name="pricePerDay"
                  placeholder="Price Per Day"
                  value={carForm.pricePerDay}
                  onChange={handleCarChange}
                />

                <input
                  type="number"
                  name="manufacturedYear"
                  placeholder="Manufactured Year"
                  value={carForm.manufacturedYear}
                  onChange={handleCarChange}
                />

                <select
                  name="seats"
                  value={carForm.seats}
                  onChange={handleCarChange}
                >
                  <option value="">Select Seats</option>

                  <option value="2">2 Seats</option>

                  <option value="4">4 Seats</option>

                  <option value="5">5 Seats</option>

                  <option value="6">6 Seats</option>

                  <option value="7">7 Seats</option>
                </select>

                <select
                  name="fuelType"
                  value={carForm.fuelType}
                  onChange={handleCarChange}
                >
                  <option value="PETROL">Petrol</option>

                  <option value="DIESEL">Diesel</option>

                  <option value="ELECTRIC">Electric</option>
                </select>

                <select
                  name="transmission"
                  value={carForm.transmission}
                  onChange={handleCarChange}
                >
                  <option value="MANUAL">Manual</option>

                  <option value="AUTOMATIC">Automatic</option>
                </select>

                <select
                  name="category"
                  value={carForm.category}
                  onChange={handleCarChange}
                >
                  <option value="SUV">SUV</option>
                  <option value="LUXURY">Luxury</option>
                  <option value="SPORTS">Sports</option>
                  <option value="SEDAN">Sedan</option>
                  <option value="VAN_MPV">Van / MPV</option>
                  <option value="ECONOMY">Economy</option>
                </select>

                <select
                  name="carStatus"
                  value={carForm.carStatus}
                  onChange={handleCarChange}
                >
                  <option value="AVAILABLE">Available</option>

                  <option value="RENTED">Rented</option>

                  <option value="MAINTENANCE">Maintenance</option>
                </select>

                <input type="file" multiple onChange={handleImageChange} />
              </div>

              <textarea
                name="description"
                placeholder="Write about the car..."
                value={carForm.description}
                onChange={handleCarChange}
                className="carDescription"
              />

              <button className="saveCarBtn" onClick={handleSaveCar}>
                {editingCar ? "Update Car" : "Add Car"}
              </button>
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

export default AdminDashboard;
