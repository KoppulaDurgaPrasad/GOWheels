import React from "react";
import "./Sidebar.css";

function Sidebar({ activeSection, setActiveSection, logout, adminData }) {
  return (
    <div className="adminSidebar">
      <div className="adminLogo">
        🚘 <span>GoWheels</span>
      </div>

      <div className="welcomeBox">
        <h3>Welcome</h3>
        <p>{adminData?.firstName || "Admin"}</p>
      </div>

      <ul className="sidebarMenu">
        <li
          onClick={() => setActiveSection("profile")}
          className={activeSection === "profile" ? "activeMenu" : ""}
        >
          <span className="menuIcon">👤</span>

          <span className="menuText">Profile</span>
        </li>

        <li
          onClick={() => setActiveSection("bookings")}
          className={activeSection === "bookings" ? "activeMenu" : ""}
        >
          <span className="menuIcon">📋</span>

          <span className="menuText">Bookings</span>
        </li>

        <li
          onClick={() => setActiveSection("cars")}
          className={activeSection === "cars" ? "activeMenu" : ""}
        >
          <span className="menuIcon">🚗</span>

          <span className="menuText">Cars</span>
        </li>

        <li className="logout" onClick={logout}>
          <span className="menuIcon">🚪</span>

          <span className="menuText">Logout</span>
        </li>
      </ul>
    </div>
  );
}

export default Sidebar;
