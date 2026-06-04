import React from "react";
import "./Cars.css";

function Cars({
  filteredCars = [],
  totalCars,
  search,
  setSearch,
  handleSearch,
  setShowFilters,
  openAddModal,
  setSelectedCar,
  setSelectedImage,
  openEditModal,
  deleteCar,
  role,
  hideAddCar = false,
  setShowBookingModal,
  setBookingCar,
}) {
  return (
    <div className="carsPage">
      <div className="topbar">
        <button className="filterBtn" onClick={() => setShowFilters(true)}>
          ☰ Filters
        </button>

        <div className="searchContainer">
          <input
            type="text"
            placeholder="Search by brand or model..."
            className="searchBar"
            value={search}
            onChange={(e) => setSearch(e.target.value)}
            onKeyDown={(e) => {
              if (e.key === "Enter") {
                handleSearch();
              }
            }}
          />

          <button className="searchBtn" onClick={handleSearch}>
            🔍
          </button>
        </div>

        {role === "ADMIN" && (
          <button className="addBtn" onClick={openAddModal}>
            {" "}
            + Add Car{" "}
          </button>
        )}
      </div>

      <div className="carCountBox">
        <h3>Total Cars: {totalCars ?? filteredCars.length}</h3>
      </div>

      {filteredCars.length === 0 ? (
        <div className="emptyCarsBox">
          <h2>No Cars Found</h2>

          <p>Try changing filters or search keyword.</p>
        </div>
      ) : (
        <div className="carGrid">
          {filteredCars.map((car) => (
            <div
              key={car.id}
              className="squareCarCard"
              onClick={() => {
                setSelectedCar(car);

                if (car.images && car.images.length > 0) {
                  setSelectedImage(car.images[0].imageUrl);
                } else {
                  setSelectedImage("");
                }
              }}
            >
              <div className="squareImageBox">
                {car.images && car.images.length > 0 ? (
                  <img
                    src={car.images[0].imageUrl}
                    alt={`${car.carBrand} ${car.carModel}`}
                    className="squareImage"
                  />
                ) : (
                  <div className="noImage">No Image</div>
                )}
              </div>

              <div className="squareInfo">
                <h2>
                  {car.carBrand} {car.carModel}
                </h2>
                <p className="carCategory">
                  {car.category === "VAN_MPV" ? "VAN/MPV" : car.category}
                </p>
                <p className="carPrice">
                  ₹{car.pricePerDay}
                  <span>/day</span>
                </p>

                <div
                  className={
                    car.carStatus === "AVAILABLE"
                      ? "statusText availableText"
                      : car.carStatus === "RENTED"
                        ? "statusText rentedText"
                        : "statusText maintenanceText"
                  }
                >
                  {car.carStatus}
                </div>

                <div className="cardActions">
                  {role === "ADMIN" ? (
                    <>
                      <button
                        className="editBtn"
                        onClick={(e) => {
                          e.stopPropagation();

                          openEditModal(car);
                        }}
                      >
                        Edit
                      </button>

                      <button
                        className="deleteBtn"
                        onClick={(e) => {
                          e.stopPropagation();

                          deleteCar(car.id);
                        }}
                      >
                        Delete
                      </button>
                    </>
                  ) : (
                    <>
                      <button
                        className="viewBtn"
                        onClick={(e) => {
                          e.stopPropagation();

                          setSelectedCar(car);

                          if (car.images && car.images.length > 0) {
                            setSelectedImage(car.images[0].imageUrl);
                          }
                        }}
                      >
                        View
                      </button>

                      {car.carStatus === "AVAILABLE" && (
                        <button
                          className="bookBtn"
                          onClick={(e) => {
                            e.stopPropagation();

                            setBookingCar(car);

                            setShowBookingModal(true);
                          }}
                        >
                          Book Now
                        </button>
                      )}
                    </>
                  )}
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default Cars;
