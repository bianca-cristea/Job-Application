import React, { useEffect, useState } from "react";
import { useAuth } from "../hooks/useAuth";

export default function Applications() {
  const { user } = useAuth();
  const [applications, setApplications] = useState([]);

  useEffect(() => {
    async function fetchData() {
      const res = await fetch("http://localhost:8080/applications", {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      });
      const data = await res.json();
      setApplications(data);
    }
    fetchData();
  }, []);

  if (!user?.roles.includes("ROLE_ADMIN")) {
    return <p>Access denied</p>;
  }

  return (
    <div className="applications-container">
          <h2 className="applications-title">Applications</h2>
          <ul className="applications-list">
            {applications.map(app => (
              <li key={app.id} className="application-item">
                <p><b>Job:</b> {app.job?.title}</p>
                <p><b>Company:</b> {app.job?.company?.name || "N/A"}</p>
                <p><b>User:</b> {app.user.username}</p>
                <p><b>Status:</b> {app.status}</p>
                <p><b>Date:</b> {new Date(app.createdAt).toLocaleString()}</p>
              </li>
            ))}
          </ul>
        </div>
  );
}
