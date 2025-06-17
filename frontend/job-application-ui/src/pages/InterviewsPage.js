import React, { useEffect, useState } from "react";
import { useAuth } from "../hooks/useAuth";

export default function InterviewsPage() {
  const { user } = useAuth();
  const [interviews, setInterviews] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchInterviews();
  }, []);

  async function fetchInterviews() {
    setLoading(true);
    try {
      const res = await fetch("http://localhost:8080/interviews", {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      });
      if (!res.ok) throw new Error("Failed to fetch interviews");
      const data = await res.json();
      setInterviews(data);
    } catch (error) {
      console.error(error);
      alert("Could not load interviews");
    } finally {
      setLoading(false);
    }
  }

  const deleteInterview = async (id) => {
    if (!window.confirm("Are you sure you want to delete this interview?")) return;
    try {
      const res = await fetch(`http://localhost:8080/interviews/${id}`, {
        method: "DELETE",
        headers: {
          Authorization: "Bearer " + localStorage.getItem("token"),
        },
      });
      if (!res.ok) throw new Error("Delete failed");
      alert("Interview deleted");

      // ✅ Scoatem din UI imediat
      setInterviews((prev) => prev.filter((i) => i.id !== id));
    } catch (err) {
      alert("Could not delete interview");
      console.error(err);
    }
  };


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
            style={{ border: "1px solid #ccc", padding: "1rem", marginBottom: "1rem" }}
          >
            <p><b>Date:</b> {new Date(i.scheduledAt).toLocaleString()}</p>
            <p><b>Location:</b> {i.location}</p>
            <p><b>Job:</b> {i.application.job.title}</p>
            <p><b>Candidate:</b> {i.application.user.email}</p>
            <button onClick={() => deleteInterview(i.id)}>Delete</button>
          </div>
        ))
      )}
    </div>
  );
}
