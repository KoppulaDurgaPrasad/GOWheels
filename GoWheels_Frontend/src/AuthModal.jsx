import React, { useState } from "react";
import "./AuthModal.css";
import API from "./api";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

function AuthModal({ onClose }) {
  const [isLogin, setIsLogin] = useState(true);
  const [showPassword, setShowPassword] = useState(false);

  const [formData, setFormData] = useState({
    email: "",
    password: "",
    confirmPassword: "",
  });

  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleLogin = async () => {
    try {
      setLoading(true);

      const res = await API.post("/api/auth/signin", {
        email: formData.email,
        password: formData.password,
      });

      localStorage.setItem("token", res.data.token);

      toast.success("Login successful 🎉");

      navigate("/dashboard");

      onClose();
    } catch (err) {
      toast.error("Invalid email or password ❌");
    } finally {
      setLoading(false);
    }
  };

  const handleRegister = async () => {
    if (formData.password !== formData.confirmPassword) {
      toast.error("Passwords do not match ❌");
      return;
    }

    try {
      setLoading(true);

      await API.post("/api/customer/register", {
        email: formData.email,
        password: formData.password,
      });

      toast.success("Registered successfully 🎉 Please login");

      setIsLogin(true);
    } catch (err) {
      toast.error("Registration failed ❌");
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    isLogin ? handleLogin() : handleRegister();
  };

  const handleGoogleLogin = () => {
    window.location.href = `${import.meta.env.VITE_API_URL}/oauth2/authorization/google`;
  };

  return (
    <div className="authOverlay" onClick={onClose}>
      <div className="authModal" onClick={(e) => e.stopPropagation()}>
        <span className="closeAuth" onClick={onClose}>
          ✕
        </span>
        <h2>{isLogin ? "Login" : "Register"}</h2>
        <form className="authForm" onSubmit={handleSubmit}>
          <input
            type="email"
            name="email"
            placeholder="Email"
            onChange={handleChange}
            required
          />

          <div className="passwordField">
            <input
              type={showPassword ? "text" : "password"}
              name="password"
              placeholder="Password"
              onChange={handleChange}
              required
            />
            <span onClick={() => setShowPassword(!showPassword)}>
              {showPassword ? "🙈" : "👁"}
            </span>
          </div>

          {!isLogin && (
            <input
              type="text"
              name="confirmPassword"
              placeholder="Confirm Password"
              onChange={handleChange}
              required
            />
          )}

          <button type="submit" disabled={loading}>
            {loading ? "Please wait..." : isLogin ? "Login" : "Register"}
          </button>

          <div className="divider">
            <span>OR</span>
          </div>

          <button
            type="button"
            className="googleBtn"
            onClick={handleGoogleLogin}
          >
            <img
              src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/google/google-original.svg"
              alt="google"
            />
            Continue with Google
          </button>
        </form>
        <p className="switchText">
          {isLogin ? "Don't have an account?" : "Already have an account?"}
          <span onClick={() => setIsLogin(!isLogin)}>
            {isLogin ? " Register" : " Login"}
          </span>
        </p>
      </div>
    </div>
  );
}

export default AuthModal;
