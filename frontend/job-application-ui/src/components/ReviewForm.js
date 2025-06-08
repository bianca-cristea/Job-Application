import React, { useState } from 'react';

export default function ReviewForm({ initialData = {}, onSubmit }) {
  const [title, setTitle] = useState(initialData.title || '');
  const [description, setDescription] = useState(initialData.description || '');
  const [rating, setRating] = useState(initialData.rating || 0);

  function handleSubmit(e) {
    e.preventDefault();
    onSubmit({ title, description, rating });
    setTitle('');
    setDescription('');
    setRating(0);
  }

  return (
    <form onSubmit={handleSubmit} style={{ marginBottom: '1rem' }}>
      <input
        type="text"
        placeholder="Title"
        value={title}
        required
        onChange={(e) => setTitle(e.target.value)}
      />
      <br />
      <textarea
        placeholder="Description"
        value={description}
        required
        onChange={(e) => setDescription(e.target.value)}
      />
      <br />
      <input
        type="number"
        placeholder="Rating"
        min="0"
        max="5"
        value={rating}
        required
        onChange={(e) => setRating(e.target.value)}
      />
      <br />
      <button type="submit">Save</button>
    </form>
  );
}
