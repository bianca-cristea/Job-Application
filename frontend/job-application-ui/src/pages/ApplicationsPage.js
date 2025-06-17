import React, { useEffect, useState } from "react";
import { useAuth } from "../hooks/useAuth";
import { getAllApplications, markApplicationAsInterview } from "../services/api";


export default function ApplicationsPage() {
  const { user } = useAuth();
  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(false);

async function loadApplications() {
  setLoading(true);
  try {
    const data = await getAllApplications();
    setApplications(data.filter((app) => app.status !== "interview"));
  } catch (error) {
    console.error(error);
    alert("Could not load applications");
  } finally {
    setLoading(false);
  }
}


  async function changeStatusToInterview(applicationId) {
    try {
      await markApplicationAsInterview(applicationId);
      await loadApplications();
    } catch (error) {
      console.error(error);
      alert("Failed to mark as interview");
    }
  }

  useEffect(() => {
    loadApplications();
  }, []);

  if (!user?.roles.includes("ROLE_ADMIN")) return <p>Access denied</p>;
  if (loading) return <p>Loading applications...</p>;

  return (
    <div className="applications-container">
      <h2 className="applications-title">Applications</h2>
      <ul className="applications-list">
        {applications.map((app) => (
          <li key={app.id} className="application-item">
            <p>
              <b>Job:</b> {app.job?.title}
            </p>
            <p>
              <b>Company:</b> {app.job?.company?.name || "N/A"}
            </p>
            <p>
              <b>User:</b> {app.user.username}
            </p>
            <p>
              <b>Status:</b> {app.status}
              {app.status !== "interview" && (
                <button
                  onClick={() => changeStatusToInterview(app.id)}
                  style={{ marginLeft: "1rem" }}
                >
                  Mark as Interview
                </button>
              )}
            </p>
            <p>
              <b>Date:</b> {new Date(app.createdAt).toLocaleString()}
            </p>
          </li>
        ))}
      </ul>
    </div>
  );
}
