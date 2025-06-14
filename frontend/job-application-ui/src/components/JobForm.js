import React, { useState, useEffect } from "react";

export default function JobForm({ onSubmit, initialData }) {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [minSalary, setMinSalary] = useState("");
  const [maxSalary, setMaxSalary] = useState("");
  const [location, setLocation] = useState("");
  const [companyId, setCompanyId] = useState("");


  useEffect(() => {
    if (initialData) {
      setTitle(initialData.title || "");
      setDescription(initialData.description || "");
      setMinSalary(initialData.minSalary || "");
      setMaxSalary(initialData.maxSalary || "");
      setLocation(initialData.location || "");
      setCompanyId(initialData.company?.id || "");
    }
  }, [initialData]);

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!title || !description) {
      alert("Please fill in all required fields");
      return;
    }

    onSubmit({
      title,
      description,
      minSalary,
      maxSalary,
      location,
      company: companyId ? { id: Number(companyId) } : null,
    });


    if (!initialData) {
      setTitle("");
      setDescription("");
      setMinSalary("");
      setMaxSalary("");
      setLocation("");
      setCompanyId("");
    }
  };

  return (
    <form onSubmit={handleSubmit} style={{ marginBottom: "1rem" }}>
      <div>
        <label>Title*</label>
        <input
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
        />
      </div>

      <div>
        <label>Description*</label>
        <textarea
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          required
        />
      </div>

      <div>
        <label>Min Salary</label>
        <input
          type="number"
          value={minSalary}
          onChange={(e) => setMinSalary(e.target.value)}
        />
      </div>

      <div>
        <label>Max Salary</label>
        <input
          type="number"
          value={maxSalary}
          onChange={(e) => setMaxSalary(e.target.value)}
        />
      </div>

      <div>
        <label>Location</label>
        <input
          type="text"
          value={location}
          onChange={(e) => setLocation(e.target.value)}
        />
      </div>

      <div>
        <label>Company ID</label>
        <input
          type="number"
          value={companyId}
          onChange={(e) => setCompanyId(e.target.value)}
        />
      </div>

      <button type="submit">{initialData ? "Update Job" : "Add Job"}</button>
    </form>
  );
}
