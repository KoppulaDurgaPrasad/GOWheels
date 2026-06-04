import React from "react";
import "./Home.css";
import Navbar from "./Navbar";

import economy from "./assets/Economy.jpeg";
import sedan from "./assets/Sedan.jpeg";
import suv from "./assets/SUV.jpeg";
import luxury from "./assets/Luxury.jpeg";
import sports from "./assets/Sports.jpeg";
import van from "./assets/VanorMPV.jpeg";
import bg from "./assets/Bg.jpeg";
import Footer from "./Footer";

const cars = [
  { name: "Economy", img: economy },
  { name: "Sedan", img: sedan },
  { name: "SUV", img: suv },
  { name: "Luxury", img: luxury },
  { name: "Sports", img: sports },
  { name: "Van / MPV", img: van },
];

function Home() {
  return (
    <div className="home">
      <Navbar />

      <section
        id="home"
        className="hero"
        style={{ backgroundImage: `url(${bg})` }}
      >
        <div className="heroText">
          <h1>Find The Perfect Ride</h1>
          <p>Luxury • Comfort • Performance</p>
        </div>
      </section>

      <section id="cars" className="categorySection">
        <div className="container">
          <h2 className="sectionTitle"> Car Class</h2>

          <div className="slider">
            <div className="slideTrack">
              {[...cars, ...cars].map((car, index) => (
                <div className="categoryCard" key={index}>
                  <img src={car.img} alt={car.name} />

                  <div className="categoryInfo">
                    <h4>{car.name}</h4>
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
      </section>

      <section id="about" className="aboutSection">
        <div className="container">
          <h2 className="sectionTitle">About GoWheels</h2>

          <div className="aboutCards">
            <div className="aboutCard">
              <h3>🚗 Premium Vehicles</h3>
              <p>
                Choose from a wide range of well-maintained cars including
                economy, SUVs, luxury and sports models for every journey.
              </p>
            </div>

            <div className="aboutCard">
              <h3>⚡ Easy Booking</h3>
              <p>
                Book your ride quickly with a smooth and user-friendly platform
                designed for convenience.
              </p>
            </div>

            <div className="aboutCard">
              <h3>🛡 Reliable Service</h3>
              <p>
                We ensure safe, clean and reliable vehicles so you can travel
                comfortably every time.
              </p>
            </div>
          </div>
        </div>
      </section>

      <section id="contact" className="contactSection">
        <div className="container">
          <h2 className="sectionTitle">Contact Us</h2>

          <div className="contactCards">
            <div className="contactCard">
              <h3>📧 Email</h3>
              <p>support@gowheels.com</p>
            </div>

            <div className="contactCard">
              <h3>📞 Phone</h3>
              <p>+91 98765 43210</p>
            </div>

            <div className="contactCard">
              <h3>📍 Location</h3>
              <p>Visakhapatnam, India</p>
            </div>
          </div>
        </div>
      </section>
      <Footer />
    </div>
  );
}

export default Home;
