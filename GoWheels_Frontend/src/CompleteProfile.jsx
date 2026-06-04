import { useEffect, useState } from "react";
import API from "./api";
import { useNavigate, useLocation } from "react-router-dom";
import { toast } from "react-toastify";
import "./CompleteProfile.css";
import Footer from "./Footer";

function CompleteProfile() {
  const navigate = useNavigate();
  const location = useLocation();

  const [profile, setProfile] = useState(null);
  const [file, setFile] = useState(null);
  const [isGoogleUser, setIsGoogleUser] = useState(false);
  const [showPassword, setShowPassword] = useState(false);

  const [form, setForm] = useState({
    firstName: "",
    lastName: "",
    gender: "",
    contactNo: "",
    dob: "",
    licenseNumber: "",
    identityProofType: "AADHAR",
    identityProofNumber: "",
    password: "",
    confirmPassword: "",
  });

  useEffect(() => {
    if (location.state?.error === "complete-profile") {
      toast.error("Please fill all profile details properly ❌");
    }
  }, [location.state]);

  useEffect(() => {
    const token = localStorage.getItem("token");

    if (!token) {
      navigate("/");
      return;
    }

    API.get("/api/customer/profile")
      .then((res) => {
        setProfile(res.data);

        if (res.data.provider === "GOOGLE") {
          setIsGoogleUser(true);
        }

        if (res.data.profileComplete === true) {
          navigate("/dashboard");
        }
      })
      .catch(() => {
        toast.error("Session expired ❌");
        localStorage.removeItem("token");
        navigate("/");
      });
  }, [navigate]);
  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };
  const progress = Math.floor(
    ([
      form.firstName,
      form.lastName,
      form.gender,
      form.dob,
      form.contactNo,
      form.licenseNumber,
      form.identityProofNumber,
      !isGoogleUser || form.password,
    ].filter(Boolean).length /
      8) *
      100,
  );
  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      if (isGoogleUser && form.password !== form.confirmPassword) {
        toast.error("Passwords do not match ❌");
        return;
      }
      if (
        !form.firstName ||
        !form.lastName ||
        !form.gender ||
        !form.dob ||
        !form.contactNo ||
        !form.licenseNumber ||
        !form.identityProofNumber
      ) {
        toast.error("Please fill all required details ❌");
        return;
      }
      await API.put("/api/customer/complete-profile", form);

      if (!file) {
        toast.error("Please upload document ❌");
        return;
      }

      const formData = new FormData();
      formData.append("file", file);

      await API.post("/api/customer/upload", formData);

      toast.success("Profile completed 🎉");

      setTimeout(() => navigate("/dashboard"), 1200);
    } catch (err) {
      console.log("PROFILE ERROR:", err);

      const status = err.response?.status;
      const message = err.response?.data;
      if (status === 401) {
        toast.error("Session expired ❌");
        localStorage.removeItem("token");
        navigate("/");
        return;
      }

      if (status === 400) {
        toast.error(message || "Please fill all required details ❌");
        return;
      }

      if (status === 500) {
        toast.error("Something went wrong ❌ Try again");
        return;
      }

      toast.error("Profile update failed ❌");
    }
  };

  if (!profile) return <div className="loading">Loading...</div>;

  return (
    <>
      <div className="page">
        <div className="profile-card">
          <h2 className="title">Complete Your Profile</h2>

          <div className="progress-container">
            <p>{progress}% Completed</p>
            <div className="progress-bar-wrapper">
              <div className="progress-bar" style={{ width: `${progress}%` }} />
            </div>
          </div>

          <form onSubmit={handleSubmit}>
            <div className="section">
              <h3>👤 Personal Info</h3>

              <input
                name="firstName"
                placeholder="First Name"
                onChange={handleChange}
              />
              <input
                name="lastName"
                placeholder="Last Name"
                onChange={handleChange}
              />

              <select name="gender" onChange={handleChange}>
                <option value="">Select Gender</option>
                <option value="MALE">Male</option>
                <option value="FEMALE">Female</option>
              </select>
              <input type="date" name="dob" onChange={handleChange} />
            </div>

            <div className="section">
              <h3>📱 Contact</h3>
              <div className="phone-container">
                <span className="country-code">+91</span>
                <input
                  name="contactNo"
                  placeholder="Mobile Number"
                  onChange={handleChange}
                />
              </div>
            </div>

            <div className="section">
              <h3>🪪 License</h3>
              <input
                name="licenseNumber"
                placeholder="License Number"
                onChange={handleChange}
              />
            </div>

            <div className="section">
              <h3>🧾 Identity Proof</h3>
              <select name="identityProofType" onChange={handleChange}>
                <option value="AADHAR">AADHAR</option>
                <option value="PAN">PAN</option>
                <option value="PASSPORT">PASSPORT</option>
              </select>
              <input
                name="identityProofNumber"
                placeholder="Proof Number"
                onChange={handleChange}
              />
            </div>

            {isGoogleUser && (
              <div className="section">
                <h3>🔐 Set Password</h3>

                <div className="password-field">
                  <input
                    type={showPassword ? "text" : "password"}
                    name="password"
                    placeholder="Password"
                    onChange={handleChange}
                  />
                  <span
                    className="eye-icon"
                    onClick={() => setShowPassword(!showPassword)}
                  >
                    {showPassword ? "🙈" : "👁"}
                  </span>
                </div>

                <input
                  type="text"
                  name="confirmPassword"
                  placeholder="Confirm Password"
                  onChange={handleChange}
                  style={{ marginTop: "14px" }}
                />
              </div>
            )}

            <div className="section">
              <h3>📄 Upload Document</h3>

              {!file ? (
                <div
                  className="upload-box"
                  onClick={() => document.getElementById("fileInput").click()}
                >
                  <span className="upload-btn">Choose File</span>
                  <span className="upload-text">No file chosen</span>

                  <input
                    id="fileInput"
                    type="file"
                    style={{ display: "none" }}
                    onChange={(e) => setFile(e.target.files[0])}
                  />
                </div>
              ) : (
                <div className="file-selected">
                  <span>{file.name}</span>
                  <button type="button" onClick={() => setFile(null)}>
                    ✕
                  </button>
                </div>
              )}
            </div>

            <button className="submit-btn">Complete Profile</button>
          </form>
        </div>
      </div>
      <Footer />
    </>
  );
}

export default CompleteProfile;
