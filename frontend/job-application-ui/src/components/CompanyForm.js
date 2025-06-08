import React, { useState } from 'react';

export default function CompanyForm({ initialData = {}, onSubmit }) {
  const [name, setName] = useState(initialData.name || '');
  const [description, setDescription] = useState(initialData.description || '');

  function handleSubmit(e) {
    e.preventDefault();
    onSubmit({ name, description });
    setName('');
    setDescription('');
  }

  return (
    <form onSubmit={handleSubmit} style={{ marginBottom: '1rem' }}>
      <input
        type="text"
        placeholder="Company name"
        value={name}
        onChange={(e) => setName(e.target.value)}
        required
      />
      <br />
      <textarea
        placeholder="Company description"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
        required
      />
      <br />
      <button type="submit">Save</button>
    </form>
  );
}
