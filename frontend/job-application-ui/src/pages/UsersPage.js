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
  const [size, setSize] = useState(10);
  const [sort, setSort] = useState('username');
  const [errorMsg, setErrorMsg] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchRoles();
    fetchUsers();
  }, [page, size, sort]);

  async function fetchUsers() {
    try {
      const data = await getAllUsers(page, size, sort);
      console.log('Fetched users data:', data);
      setUsers(data.content);
      setTotalPages(data.totalPages);
    } catch (err) {
      console.error(err);
      setErrorMsg('Failed to load users.');
    }
  }

  async function fetchRoles() {
    try {
      const data = await getAllRoles();
      setRoles(data);
    } catch (err) {
      console.error(err);
      setErrorMsg('Failed to load roles.');
    }
  }

  async function handleCreateOrUpdate(e) {
    e.preventDefault();
    setLoading(true);
    setErrorMsg('');

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
      setPage(0);
      await fetchUsers();
    } catch (err) {
      console.error(err);
      setErrorMsg(err.message || 'Something went wrong while saving user.');
    } finally {
      setLoading(false);
    }
  }

  function handleEdit(user) {
    setEditId(user.id);
    setUsername(user.username);
    setPassword('');
    setSelectedRoles(user.roles.map(role => role.id));
    setErrorMsg('');
  }

  async function handleDelete(id) {
    if (window.confirm('Are you sure?')) {
      try {
        setLoading(true);
        await deleteUser(id);
        setPage(0);
        await fetchUsers();
        setErrorMsg('');
      } catch (err) {
        console.error(err);
        setErrorMsg('Failed to delete user.');
      } finally {
        setLoading(false);
      }
    }
  }

  function handleSizeChange(e) {
    setSize(Number(e.target.value));
    setPage(0);
  }

  function handleSortChange(e) {
    setSort(e.target.value);
    setPage(0);
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
          disabled={loading}
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          required={!editId}
          onChange={(e) => setPassword(e.target.value)}
          disabled={loading}
        />
        <select
          multiple
          value={selectedRoles}
          size={2}
          onChange={(e) =>
            setSelectedRoles(Array.from(e.target.selectedOptions, opt => Number(opt.value)))
          }
          disabled={loading}
        >
          {roles.map((r) => (
            <option key={r.id} value={r.id}>{r.name}</option>
          ))}
        </select>
        <button type="submit" disabled={loading}>
          {loading ? (editId ? 'Updating...' : 'Creating...') : (editId ? 'Update' : 'Create')}
        </button>
        {editId && (
          <button
            type="button"
            onClick={() => {
              setEditId(null);
              setUsername('');
              setPassword('');
              setSelectedRoles([]);
              setErrorMsg('');
            }}
            disabled={loading}
            style={{ marginLeft: '0.5rem' }}
          >
            Cancel
          </button>
        )}
      </form>

      {errorMsg && (
        <div style={{ color: 'red', marginTop: '0.5rem' }}>
          {errorMsg}
        </div>
      )}

      <div style={{ margin: '1rem 0' }}>
        <label>
          Sort by:{' '}
          <select value={sort} onChange={handleSortChange} disabled={loading}>
            <option value="username">Username</option>
            <option value="id">ID</option>
          </select>
        </label>

        <label style={{ marginLeft: '1rem' }}>
          Items per page:{' '}
          <select value={size} onChange={handleSizeChange} disabled={loading}>
            <option value={5}>5</option>
            <option value={10}>10</option>
            <option value={20}>20</option>
          </select>
        </label>
      </div>

      <h3>
        All Users (Page {page + 1} / {totalPages})
      </h3>
      <ul>
        {users.map((u) => (
          <li key={u.id}>
            <strong>{u.username}</strong> — Roles: {u.roles.map(r => r.name).join(', ')}
            <button
              onClick={() => handleEdit(u)}
              style={{ marginLeft: '1rem' }}
              disabled={loading}
            >
              Edit
            </button>
            <button
              onClick={() => handleDelete(u.id)}
              style={{ marginLeft: '0.5rem' }}
              disabled={loading}
            >
              Delete
            </button>
          </li>
        ))}
      </ul>

      <div style={{ marginTop: '1rem' }}>
        <button disabled={page <= 0 || loading} onClick={() => setPage(p => p - 1)}>Prev</button>
        <button disabled={page >= totalPages - 1 || loading} onClick={() => setPage(p => p + 1)}>Next</button>
      </div>
    </div>
  );
}
