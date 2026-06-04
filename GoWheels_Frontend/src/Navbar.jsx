import React, { useState, useEffect } from "react";
import "./Navbar.css";
import logo from "./assets/Logo.png";
import AuthModal from "./AuthModal";

function Navbar() {
  const [menuOpen, setMenuOpen] = useState(false);
  const [active, setActive] = useState("home");
  const [showAuth, setShowAuth] = useState(false);

  useEffect(() => {
    const handleScroll = () => {
      const sections = ["home", "cars", "about", "contact"];

      sections.forEach((id) => {
        const section = document.getElementById(id);
        if (section) {
          const top = section.offsetTop - 80;
          const height = section.offsetHeight;

          if (window.scrollY >= top && window.scrollY < top + height) {
            setActive(id);
          }
        }
      });
    };

    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  const scrollToSection = (id) => {
    setMenuOpen(false);
    document.getElementById(id).scrollIntoView({
      behavior: "smooth",
    });
  };

  return (
    <nav className="navbar">
      <div className="logo" onClick={() => scrollToSection("home")}>
        <img src={logo} alt="GoWheels Logo" className="logoImg" />
      </div>
      <ul className="navLinks desktopMenu">
        <li
          className={active === "home" ? "active" : ""}
          onClick={() => scrollToSection("home")}
        >
          Home
        </li>
        <li
          className={active === "cars" ? "active" : ""}
          onClick={() => scrollToSection("cars")}
        >
          Class
        </li>
        <li
          className={active === "about" ? "active" : ""}
          onClick={() => scrollToSection("about")}
        >
          About
        </li>
        <li
          className={active === "contact" ? "active" : ""}
          onClick={() => scrollToSection("contact")}
        >
          Contact
        </li>
      </ul>

      <div className="rightSection">
        <button
          className="loginBtn desktopMenu"
          onClick={() => setShowAuth(true)}
        >
          Login / Register
        </button>

        <div className="hamburger" onClick={() => setMenuOpen(true)}>
          <span></span>
          <span></span>
          <span></span>
        </div>
      </div>

      <div className={`sidebar ${menuOpen ? "open" : ""}`}>
        <div className="closeBtn" onClick={() => setMenuOpen(false)}>
          ✕
        </div>

        <ul className="sidebarMenu">
          <li onClick={() => scrollToSection("home")}>Home</li>
          <li onClick={() => scrollToSection("cars")}>Class</li>
          <li onClick={() => scrollToSection("about")}>About</li>
          <li onClick={() => scrollToSection("contact")}>Contact</li>

          <button
            className="loginBtn mobileLogin"
            onClick={() => {
              setMenuOpen(false);
              setShowAuth(true);
            }}
          >
            Login / Register
          </button>
        </ul>
      </div>

      {menuOpen && (
        <div className="overlay" onClick={() => setMenuOpen(false)}></div>
      )}

      {showAuth && <AuthModal onClose={() => setShowAuth(false)} />}
    </nav>
  );
}

export default Navbar;
