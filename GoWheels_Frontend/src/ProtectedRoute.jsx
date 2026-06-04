import { useEffect, useState } from "react";
import { Navigate, useLocation } from "react-router-dom";
import API from "./api";
import { jwtDecode } from "jwt-decode";

const ProtectedRoute = ({ children }) => {
  const [loading, setLoading] = useState(true);
  const [profileComplete, setProfileComplete] = useState(null);

  const token = localStorage.getItem("token");
  const location = useLocation();

  useEffect(() => {
    if (!token) {
      setLoading(false);
      return;
    }

    try {
      const decoded = jwtDecode(token);
      if (decoded.role === "ADMIN") {
        setProfileComplete(true);
        setLoading(false);
        return;
      }
      API.get("/api/customer/profile-status")
        .then((res) => {
          setProfileComplete(res.data);

          setLoading(false);
        })
        .catch(() => {
          setProfileComplete(false);
          setLoading(false);
        });
    } catch (err) {
      setProfileComplete(false);
      setLoading(false);
    }
  }, [token]);

  if (loading) return <div>Loading...</div>;

  if (!token) return <Navigate to="/" replace />;

  if (profileComplete === false && location.pathname !== "/profile") {
    return (
      <Navigate to="/profile" replace state={{ error: "complete-profile" }} />
    );
  }

  return children;
};

export default ProtectedRoute;
