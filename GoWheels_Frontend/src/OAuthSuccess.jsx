import { useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import API from "./api";

function OAuthSuccess() {
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const token = params.get("token");

    if (!token) {
      navigate("/");
      return;
    }

    localStorage.setItem("token", token);

    const decoded = jwtDecode(token);

    if (decoded.role === "ADMIN") {
      navigate("/admin/dashboard");
      return;
    }

    API.get("/api/customer/profile")
      .then((res) => {
        if (res.data.isProfileComplete) {
          navigate("/dashboard");
        } else {
          navigate("/profile");
        }
      })
      .catch(() => navigate("/"));
  }, []);

  return <div>Logging in...</div>;
}

export default OAuthSuccess;
