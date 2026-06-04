import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./Home";
import CompleteProfile from "./CompleteProfile";
import OAuthSuccess from "./OAuthSuccess";
import ProtectedRoute from "./ProtectedRoute";
import AdminDashboard from "./AdminDashboard";
import CustomerDashboard from "./CustomerDashboard";

import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
function App() {
  return (
    <BrowserRouter>
      <ToastContainer
        position="top-right"
        autoClose={2000}
        className="toast-container"
      />

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/profile" element={<CompleteProfile />} />
        <Route path="/oauth-success" element={<OAuthSuccess />} />

        <Route
          path="/dashboard"
          element={
            <ProtectedRoute>
              <CustomerDashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/dashboard"
          element={
            <ProtectedRoute>
              <AdminDashboard />
            </ProtectedRoute>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
