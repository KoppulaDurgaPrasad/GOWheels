import React, { useEffect, useState } from "react";

import "./Profile.css";

import API from "./api";

import { toast } from "react-toastify";

function Profile({ user, role = "CUSTOMER", refreshProfile }) {
  const [isEditing, setIsEditing] = useState(false);

  const [showPasswordFields, setShowPasswordFields] = useState(false);

  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    contactNo: "",
    gender: "",
    dob: "",

    password: "",
    confirmPassword: "",
  });

  useEffect(() => {
    if (user) {
      setFormData({
        firstName: user.firstName || "",

        lastName: user.lastName || "",

        contactNo: user.contactNo || "",

        gender: user.gender || "",

        dob: user.dob || "",

        password: "",

        confirmPassword: "",
      });
    }
  }, [user]);

  if (!user) {
    return (
      <div className="profileLoading">
        <h2>Loading Profile...</h2>
      </div>
    );
  }

  const handleChange = (e) => {
    setFormData({
      ...formData,

      [e.target.name]: e.target.value,
    });
  };

  const handleUpdateProfile = async () => {
    try {
      await API.put(
        role === "ADMIN"
          ? "/api/admin/update-profile"
          : "/api/customer/update-profile",

        formData,
      );
      if (refreshProfile) {
        await refreshProfile();
      }

      toast.success("Profile updated successfully");

      setIsEditing(false);

      setShowPasswordFields(false);
    } catch (err) {
      toast.error("Update failed");
    }
  };

  const profileData = {
    name: `${user.firstName || ""}
       ${user.lastName || ""}`,

    email: user.email || "",

    phone: user.contactNo || "",

    gender: user.gender || "",

    dob: user.dob || "",

    role: role || "CUSTOMER",

    profileImage:
      user.profileImage ||
      (role === "CUSTOMER"
        ? user.gender === "FEMALE"
          ? "https://cdn-icons-png.flaticon.com/512/4140/4140048.png"
          : "https://cdn-icons-png.flaticon.com/512/4140/4140037.png"
        : "https://cdn-icons-png.flaticon.com/512/3135/3135715.png"),
  };

  return (
    <div className="profilePage">
      <div className="profileCard">
        <div className="profileTopSection">
          <img
            src={profileData.profileImage}
            alt="profile"
            className="profileAvatar"
          />

          <div className="profileTopInfo">
            <h1>{profileData.name}</h1>

            <p className="profileRole">{profileData.role}</p>

            <p className="profileEmail">{profileData.email}</p>
          </div>
        </div>

        <div className="profileInfoContainer">
          <div className="profileInfoBox">
            <h2>Personal Information</h2>

            <div className="infoRow">
              <span>First Name</span>

              {isEditing ? (
                <input
                  type="text"
                  name="firstName"
                  value={formData.firstName}
                  onChange={handleChange}
                  className="profileInput"
                />
              ) : (
                <p>{formData.firstName}</p>
              )}
            </div>

            <div className="infoRow">
              <span>Last Name</span>

              {isEditing ? (
                <input
                  type="text"
                  name="lastName"
                  value={formData.lastName}
                  onChange={handleChange}
                  className="profileInput"
                />
              ) : (
                <p>{formData.lastName}</p>
              )}
            </div>

            <div className="infoRow">
              <span>Email</span>

              <p>{profileData.email}</p>
            </div>

            <div className="infoRow">
              <span>Phone</span>

              {isEditing ? (
                <input
                  type="text"
                  name="contactNo"
                  value={formData.contactNo}
                  onChange={handleChange}
                  className="profileInput"
                />
              ) : (
                <p>{formData.contactNo}</p>
              )}
            </div>

            <div className="infoRow">
              <span>Gender</span>

              {isEditing ? (
                <select
                  name="gender"
                  value={formData.gender}
                  onChange={handleChange}
                  className="profileInput"
                >
                  <option value="">Select</option>

                  <option value="MALE">MALE</option>

                  <option value="FEMALE">FEMALE</option>
                </select>
              ) : (
                <p>{formData.gender}</p>
              )}
            </div>

            <div className="infoRow">
              <span>DOB</span>

              {isEditing ? (
                <input
                  type="date"
                  name="dob"
                  value={formData.dob}
                  onChange={handleChange}
                  className="profileInput"
                />
              ) : (
                <p>{formData.dob}</p>
              )}
            </div>

            <div className="infoRow">
              <span>Age</span>

              <p>{user.age || "Not Added"}</p>
            </div>
          </div>

          <div className="profileInfoBox">
            <h2>Account Details</h2>

            <div className="infoRow">
              <span>Role</span>

              <p>{profileData.role}</p>
            </div>

            {role === "CUSTOMER" && (
              <div className="infoRow">
                <span>License Number</span>

                <p>{user.licenseNumber || "Not Added"}</p>
              </div>
            )}

            {role === "CUSTOMER" && (
              <div className="infoRow">
                <span>Identity Proof Type</span>

                <p>{user.identityProofType || "Not Added"}</p>
              </div>
            )}

            {role === "CUSTOMER" && (
              <div className="infoRow">
                <span>Identity Proof Number</span>

                <p>{user.identityProofNumber || "Not Added"}</p>
              </div>
            )}

            {role === "CUSTOMER" && (
              <div className="infoRow">
                <span>Documents</span>

                <div className="documentList">
                  {!isEditing &&
                    Array.isArray(user.documents) &&
                    user.documents.length > 0 &&
                    user.documents.map((doc) => (
                      <a
                        key={doc.id}
                        href={`${import.meta.env.VITE_API_URL}/api/customer/view/${doc.id}`}
                        target="_blank"
                        rel="noreferrer"
                        className="documentLink"
                      >
                        👁 View
                      </a>
                    ))}

                  {isEditing && (
                    <div className="documentEditSection">
                      {Array.isArray(user.documents) &&
                      user.documents.length > 0
                        ? user.documents.map((doc) => (
                            <button
                              key={doc.id}
                              className="deleteDocBtn"
                              onClick={async () => {
                                try {
                                  await API.delete(
                                    `/api/customer/document/${doc.id}`,
                                  );

                                  user.documents = user.documents.filter(
                                    (d) => d.id !== doc.id,
                                  );

                                  await refreshProfile();

                                  toast.success("Document deleted");
                                } catch {
                                  toast.error("Delete failed");
                                }
                              }}
                            >
                              Delete Document
                            </button>
                          ))
                        : null}

                      <label className="uploadBox">
                        <input
                          type="file"
                          hidden
                          onChange={async (e) => {
                            try {
                              const data = new FormData();

                              data.append("file", e.target.files[0]);

                              await API.post("/api/customer/upload", data, {
                                headers: {
                                  "Content-Type": "multipart/form-data",
                                },
                              });

                              await refreshProfile();

                              toast.success("Document uploaded");
                            } catch {
                              toast.error("Upload failed");
                            }
                          }}
                        />

                        <div className="uploadContent">
                          <button type="button" className="uploadBtn">
                            Choose Files
                          </button>

                          <p>No file chosen</p>
                        </div>
                      </label>
                    </div>
                  )}
                </div>
              </div>
            )}

            <div className="infoRow">
              <span>Status</span>

              <p className="activeStatus">Active</p>
            </div>
          </div>
        </div>

        {showPasswordFields && (
          <div className="passwordSection">
            <h2>Change Password</h2>

            <input
              type="password"
              name="password"
              placeholder="New Password"
              value={formData.password}
              onChange={handleChange}
              className="profileInput"
            />

            <input
              type="password"
              name="confirmPassword"
              placeholder="Confirm Password"
              value={formData.confirmPassword}
              onChange={handleChange}
              className="profileInput"
            />
          </div>
        )}

        <div className="profileButtonContainer">
          {!isEditing ? (
            <button
              className="editProfileBtn"
              onClick={() => setIsEditing(true)}
            >
              Edit Profile
            </button>
          ) : (
            <button className="editProfileBtn" onClick={handleUpdateProfile}>
              Save Profile
            </button>
          )}

          {isEditing && (
            <button
              className="cancelProfileBtn"
              onClick={() => {
                setIsEditing(false);

                setShowPasswordFields(false);

                setFormData({
                  firstName: user.firstName || "",

                  lastName: user.lastName || "",

                  contactNo: user.contactNo || "",

                  gender: user.gender || "",

                  dob: user.dob || "",

                  password: "",

                  confirmPassword: "",
                });
              }}
            >
              Cancel
            </button>
          )}

          <button
            className="changePasswordBtn"
            onClick={() => setShowPasswordFields(!showPasswordFields)}
          >
            {showPasswordFields ? "Hide Password" : "Change Password"}
          </button>
        </div>
      </div>
    </div>
  );
}

export default Profile;
