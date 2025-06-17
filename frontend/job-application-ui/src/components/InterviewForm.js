import React, { useState } from "react";

export default function InterviewForm({ initialData = {}, onSubmit }) {
  const [scheduledAt, setScheduledAt] = useState(
    initialData.scheduledAt ? initialData.scheduledAt.substring(0, 16) : ""
  );
  const [location, setLocation] = useState(initialData.location || "");

  function handleSubmit(e) {
    e.preventDefault();
    onSubmit({ scheduledAt, location });
  }

  return (
    <form
      onSubmit={handleSubmit}
      style={{ border: "1px solid #000", padding: "1rem", marginTop: "1rem" }}
    >
      <label>
        Date & Time:
        <input
          type="datetime-local"
          value={scheduledAt}
          required
          onChange={(e) => setScheduledAt(e.target.value)}
        />
      </label>
      <br />
      <label>
        Location:
        <input
          type="text"
          value={location}
          required
          onChange={(e) => setLocation(e.target.value)}
        />
      </label>
      <br />
      <button type="submit">Save Interview</button>
      <button type="button" onClick={() => onSubmit(null)} style={{ marginLeft: "1rem" }}>
        Cancel
      </button>
    </form>
  );
}
