import React, { useEffect, useState } from 'react';
import {
  getAllUsers,
  createUser,
  updateUser,
  deleteUser,
  getAllRoles,
} from '../services/api';

export default function UserPage() {
  const [users, setUsers] = useState([]);
  const [roles, setRoles] = useState([]);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [selectedRoles, setSelectedRoles] = useState([]);
  const [editId, setEditId] = useState(null);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  useEffect(() => {
    fetchRoles();
    fetchUsers();
  }, [page]);

  async function fetchUsers() {
    try {
      const data = await getAllUsers(page);
      setUsers(data.content);
      setTotalPages(data.totalPages);
    } catch (err) {
      console.error(err);
    }
  }

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
    const user = { username, password, roles: selectedRoles.map(id => ({ id })) };

    try {
      if (editId) {
        await updateUser(editId, user);
        setEditId(null);
      } else {
        await createUser(user);
      }

      setUsername('');
      setPassword('');
      setSelectedRoles([]);
      fetchUsers();
    } catch (err) {
      console.error(err);
    }
  }

  function handleEdit(user) {
    setEditId(user.id);
    setUsername(user.username);
    setPassword('');
    setSelectedRoles(user.roles.map(role => role.id));
  }

  async function handleDelete(id) {
    if (window.confirm('Are you sure?')) {
      await deleteUser(id);
      fetchUsers();
    }
  }

  return (
    <div style={{ padding: '1rem' }}>
      <h2>User Management</h2>
      <form onSubmit={handleCreateOrUpdate}>
        <input
          type="text"
          placeholder="Username"
          value={username}
          required
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          required={!editId}
          onChange={(e) => setPassword(e.target.value)}
        />
        <select
          multiple
          value={selectedRoles}
          onChange={(e) =>
            setSelectedRoles(Array.from(e.target.selectedOptions, opt => Number(opt.value)))
          }
        >
          {roles.map((r) => (
            <option key={r.id} value={r.id}>{r.name}</option>
          ))}
        </select>
        <button type="submit">{editId ? 'Update' : 'Create'}</button>
        {editId && <button onClick={() => {
          setEditId(null);
          setUsername('');
          setPassword('');
          setSelectedRoles([]);
        }}>Cancel</button>}
      </form>

      <h3>All Users (Page {page + 1}/{totalPages})</h3>
      <ul>
        {users.map((u) => (
          <li key={u.id}>
            <strong>{u.username}</strong> — Roles: {u.roles.map(r => r.name).join(', ')}
            <button onClick={() => handleEdit(u)} style={{ marginLeft: '1rem' }}>Edit</button>
            <button onClick={() => handleDelete(u.id)} style={{ marginLeft: '0.5rem' }}>Delete</button>
          </li>
        ))}
      </ul>

      <div style={{ marginTop: '1rem' }}>
        <button disabled={page <= 0} onClick={() => setPage(p => p - 1)}>Prev</button>
        <button disabled={page >= totalPages - 1} onClick={() => setPage(p => p + 1)}>Next</button>
      </div>
    </div>
  );
}
