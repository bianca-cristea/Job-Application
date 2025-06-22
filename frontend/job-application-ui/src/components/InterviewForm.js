import React, { useState } from "react";

export default function InterviewForm({ initialData = {}, onSubmit, onCancel }) {
  const [scheduledAt, setScheduledAt] = useState(
    initialData.scheduledAt ? initialData.scheduledAt.substring(0, 16) : ""
  );
  const [location, setLocation] = useState(initialData.location || "");
  const [applicationId, setApplicationId] = useState(initialData.applicationId || "");

  function handleSubmit(e) {
    e.preventDefault();
    onSubmit({
      scheduledAt: new Date(scheduledAt).toISOString(),
      location,
      applicationId,
    });
console.log({
  scheduledAt: new Date(scheduledAt).toISOString(),
  location,
  applicationId,
});

  }



  return (
    <form
      onSubmit={handleSubmit}
      style={{ border: "1px solid #000", padding: "1rem", marginTop: "1rem" }}
    >
    <label>
      Application ID:
      <input
        type="number"
        value={applicationId}
        required
        onChange={(e) => setApplicationId(e.target.value)}
      />
    </label>

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
      <button type="button" onClick={onCancel} style={{ marginLeft: "1rem" }}>
        Cancel
      </button>
    </form>
  );
}
