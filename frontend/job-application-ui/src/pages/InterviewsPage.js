import React, { useEffect, useState } from "react";
import InterviewForm from "../components/InterviewForm";

const API_BASE = "http://localhost:8080";

export default function InterviewsPage() {
  const [interviews, setInterviews] = useState([]);
  const [loading, setLoading] = useState(false);
  const [editingInterview, setEditingInterview] = useState(null);
  const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
    // Exemplu de extragere rol din localStorage (ajustează după cum stochezi rolul)
    const userInfo = JSON.parse(localStorage.getItem("userInfo") || "{}");
    setIsAdmin(userInfo.role === "ADMIN");

    fetchInterviews();
  }, []);

  async function fetchInterviews() {
    setLoading(true);
    try {
      const res = await fetch(`${API_BASE}/interviews/dto`, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      });
      if (!res.ok) throw new Error("Failed to fetch interviews");
      const data = await res.json();
      setInterviews(data);
    } catch (error) {
      alert("Could not load interviews");
      console.error(error);
    } finally {
      setLoading(false);
    }
  }

  async function updateInterview(id, interview) {
    try {
      const res = await fetch(`${API_BASE}/interviews/${id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
        body: JSON.stringify({
          scheduledAt: interview.scheduledAt,
        }),
      });
      if (!res.ok) throw new Error("Update failed");
      alert("Interview updated");
      setEditingInterview(null);
      fetchInterviews();
    } catch (err) {
      alert("Could not update interview");
      console.error(err);
    }
  }

  if (loading) return <p>Loading interviews...</p>;

  return (
    <div style={{ padding: "1rem" }}>
      <h1>All Interviews</h1>

      {interviews.length === 0 ? (
        <p>No interviews scheduled</p>
      ) : (
        interviews.map((i) => (
          <div
            key={i.id}
            style={{
              border: "1px solid #ccc",
              padding: "1rem",
              marginBottom: "1rem",
            }}
          >
            <p>
              <b>Date:</b>{" "}
              {i.scheduledAt
                ? new Date(i.scheduledAt).toLocaleString()
                : "Not scheduled"}
            </p>
            <p>
              <b>Job:</b> {i.jobTitle || "N/A"}
            </p>
            <p>
              <b>Candidate:</b> {i.candidateName || "N/A"}
            </p>
            <p>
              <b>Company:</b> {i.companyName || "N/A"}
            </p>

            {isAdmin && !i.scheduledAt && (
              <button onClick={() => setEditingInterview(i)}>Choose date</button>
            )}

            {isAdmin && editingInterview?.id === i.id && (
              <div
                style={{
                  border: "2px solid #007bff",
                  padding: "1rem",
                  marginTop: "1rem",
                }}
              >
                <InterviewForm
                  initialData={{
                    scheduledAt: i.scheduledAt
                      ? i.scheduledAt.substring(0, 16)
                      : "",
                    applicationId: i.applicationId || "",
                  }}
                  onSubmit={(data) => updateInterview(i.id, data)}
                  onCancel={() => setEditingInterview(null)}
                />
              </div>
            )}
          </div>
        ))
      )}
    </div>
  );
}
