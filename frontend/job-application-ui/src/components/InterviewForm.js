import React, { useState } from "react";

export default function InterviewForm({ initialData = {}, onSubmit, onCancel }) {
  const [scheduledAt, setScheduledAt] = useState(
    initialData.scheduledAt ? initialData.scheduledAt.substring(0, 16) : ""
  );

  function handleSubmit(e) {
    e.preventDefault();
    onSubmit({
      scheduledAt: scheduledAt,
    });
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
      <button type="submit">Save Interview</button>
      <button type="button" onClick={onCancel} style={{ marginLeft: "1rem" }}>
        Cancel
      </button>
    </form>
  );
}
