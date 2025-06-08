import React, { useState } from 'react';

export default function InterviewForm({ initialData = {}, onSubmit }) {
  const [scheduledAt, setScheduledAt] = useState(
    initialData.scheduledAt ? initialData.scheduledAt.substring(0, 16) : ''
  );
  const [location, setLocation] = useState(initialData.location || '');

  function handleSubmit(e) {
    e.preventDefault();
    onSubmit({ scheduledAt, location });
  }

  return (
    <form onSubmit={handleSubmit}>
      <label>Date & Time:</label>
      <input
        type="datetime-local"
        value={scheduledAt}
        required
        onChange={(e) => setScheduledAt(e.target.value)}
      />
      <br />
      <label>Location:</label>
      <input
        type="text"
        value={location}
        required
        onChange={(e) => setLocation(e.target.value)}
      />
      <br />
      <button type="submit">Save</button>
    </form>
  );
}
