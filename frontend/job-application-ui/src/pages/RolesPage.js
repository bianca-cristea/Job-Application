import React, { useEffect, useState } from 'react';
import {
  getAllRoles,
  createRole,
  updateRole,
  deleteRole,
} from '../services/api';

export default function RolePage() {
  const [roles, setRoles] = useState([]);
  const [name, setName] = useState('');
  const [editId, setEditId] = useState(null);

  useEffect(() => {
    fetchRoles();
  }, []);

  async function fetchRoles() {
    try {
      const data = await getAllRoles();
      setRoles(data);
    } catch (err) {
      console.error(err);
    }
  }

  async function handleCreateOrUpdate(e) {
    e.preventDefault();
    try {
      if (editId) {
        await updateRole(editId, { name });
        setEditId(null);
      } else {
        await createRole({ name });
      }
      setName('');
      fetchRoles();
    } catch (err) {
      console.error(err);
    }
  }

  async function handleDelete(id) {
    if (window.confirm('Are you sure you want to delete this role?')) {
      await deleteRole(id);
      fetchRoles();
    }
  }

  function handleEdit(role) {
    setEditId(role.id);
    setName(role.name);
  }

  return (
    <div style={{ padding: '1rem' }}>
      <h2>Role Management</h2>
      <form onSubmit={handleCreateOrUpdate}>
        <input
          type="text"
          placeholder="Role name"
          value={name}
          required
          onChange={(e) => setName(e.target.value)}
        />
        <button type="submit">{editId ? 'Update' : 'Add'}</button>
        {editId && (
          <button type="button" onClick={() => {
            setEditId(null);
            setName('');
          }}>
            Cancel
          </button>
        )}
      </form>

      <ul>
        {roles.map((role) => (
          <li key={role.id}>
            {role.name}
            <button onClick={() => handleEdit(role)} style={{ marginLeft: '1rem' }}>Edit</button>
            <button onClick={() => handleDelete(role.id)} style={{ marginLeft: '0.5rem' }}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
}
