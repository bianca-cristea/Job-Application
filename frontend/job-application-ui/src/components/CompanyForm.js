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
    <form
      onSubmit={handleSubmit}
      style={{
        marginBottom: '1rem',
        display: 'flex',
        flexDirection: 'column',
        gap: '0.75rem',
        maxWidth: '400px',
      }}
    >
      <input
        type="text"
        placeholder="Company name"
        value={name}
        onChange={(e) => setName(e.target.value)}
        required
        style={{
          padding: '0.5rem 0.75rem',
          fontSize: '1rem',
          borderRadius: '6px',
          border: '1px solid #ccc',
          outline: 'none',
          boxSizing: 'border-box',
        }}
      />
      <textarea
        placeholder="Company description"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
        required
        style={{
          padding: '0.5rem 0.75rem',
          fontSize: '1rem',
          borderRadius: '6px',
          border: '1px solid #ccc',
          outline: 'none',
          resize: 'vertical',
          minHeight: '70px',
          boxSizing: 'border-box',
        }}
      />
      <button
        type="submit"
        style={{
          padding: '0.6rem 1.2rem',
          fontWeight: '600',
          color: 'white',
          backgroundColor: '#4a90e2',
          border: 'none',
          borderRadius: '6px',
          cursor: 'pointer',
          transition: 'background-color 0.2s ease',
        }}
        onMouseOver={e => e.currentTarget.style.backgroundColor = '#357ABD'}
        onMouseOut={e => e.currentTarget.style.backgroundColor = '#4a90e2'}
      >
        Save
      </button>
    </form>
  );
}
