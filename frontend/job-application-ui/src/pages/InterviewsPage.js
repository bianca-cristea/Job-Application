import React, { useEffect, useState } from "react";
import { useAuth } from "../hooks/useAuth";
import InterviewForm from "../components/InterviewForm";

const API_BASE = "http://localhost:8080";

export default function InterviewsPage() {
  const { user } = useAuth();
  const [interviews, setInterviews] = useState([]);
  const [loading, setLoading] = useState(false);
  const [showForm, setShowForm] = useState(false);

  useEffect(() => {
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
      console.log("Interviews fetched:", data);
      setInterviews(data);
    } catch (error) {
      console.error(error);
      alert("Could not load interviews");
    } finally {
      setLoading(false);
    }
  }

async function createInterview(interview) {
  const { applicationId, location, scheduledAt } = interview;

  try {
    const res = await fetch(`${API_BASE}/interviews/application/${applicationId}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
      body: JSON.stringify({
        location,
        scheduledAt: scheduledAt || null
      }),
    });

    if (!res.ok) throw new Error("Create failed");
    alert("Interview added");
    setShowForm(false);
    fetchInterviews();
  } catch (err) {
    alert("Could not create interview");
    console.error(err);
  }
}

const deleteInterview = async (id) => {
  if (!window.confirm("Are you sure you want to delete this interview?")) return;
  try {
    const res = await fetch(`${API_BASE}/interviews/${id}`, {
      method: "DELETE",
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
    });

    if (!res.ok) throw new Error("Delete failed");

    alert("Interview deleted");
    await fetchInterviews();
  } catch (err) {
    alert("Could not delete interview");
    console.error(err);
  }
};


  if (loading) return <p>Loading interviews...</p>;

  return (
    <div style={{ padding: "1rem" }}>
      <h1>All Interviews</h1>

      {!showForm && (
        <button onClick={() => setShowForm(true)}>Add Interview</button>
      )}

      {showForm && (
        <InterviewForm
          onSubmit={createInterview}
          onCancel={() => setShowForm(false)}
        />
      )}

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
            <p><b>Date:</b> {i.scheduledAt ? new Date(i.scheduledAt).toLocaleString() : "N/A"}</p>
            <p><b>Location:</b> {i.location || "N/A"}</p>
            <p><b>Candidate:</b> {i.candidateName || "N/A"}</p>
            <p><b>Company:</b> {i.companyName || "N/A"}</p>
            <button onClick={() => deleteInterview(i.id)}>Delete</button>
          </div>
        ))
      )}
    </div>
  );
}
