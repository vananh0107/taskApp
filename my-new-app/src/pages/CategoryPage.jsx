import React, { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import {
  fetchCategories,
  createCategory,
  deleteCategory,
} from '../actions/categoryActions';

const CategoryPage = () => {
  const dispatch = useDispatch();
  const categories = useSelector((state) => state.categories);
  const [name, setName] = useState('');
  const [refresh, setRefresh] = useState(false);
  const [editingId, setEditingId] = useState(null);
  const [editingName, setEditingName] = useState('');
  useEffect(() => {
    setTimeout(() => {
      dispatch(fetchCategories());
      setRefresh(false);
    }, 500);
  }, [refresh]);

  const handleCreate = (e) => {
    e.preventDefault();
    if (name.trim()) {
      dispatch(createCategory({ name }));
      setName('');
    }
    setRefresh(true);
  };

  const handleDelete = (id) => {
    dispatch(deleteCategory(id));
    setRefresh(true);
  };

  const handleEditClick = (category) => {
    setEditingId(category.id);
    setEditingName(category.name);
  };

  const handleCancelEdit = () => {
    setEditingId(null);
    setEditingName('');
  };

  const handleSaveEdit = () => {
    if (editingName.trim()) {
      dispatch(createCategory({ id: editingId, name: editingName }));
      setEditingId(null);
      setEditingName('');
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 p-8">
      <div className="max-w-3xl mx-auto bg-white p-6 rounded-lg shadow-lg">
        <h2 className="text-2xl font-bold mb-6 text-center">
          Manage Categories
        </h2>

        <form onSubmit={handleCreate} className="flex gap-4 mb-6">
          <input
            type="text"
            placeholder="New category name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
            className="flex-1 border rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
          <button
            type="submit"
            className="bg-blue-500 hover:bg-blue-600 text-white font-bold px-4 py-2 rounded"
          >
            Create
          </button>
        </form>

        <div className="overflow-x-auto">
          <table className="min-w-full bg-white">
            <thead>
              <tr>
                <th className="py-2 px-4 border-b text-left">Category Name</th>
                <th className="py-2 px-4 border-b text-center">Actions</th>
              </tr>
            </thead>
            <tbody>
              {categories?.map((cat) => (
                <tr key={cat.id} className="hover:bg-gray-50">
                  <td className="py-2 px-4 border-b">
                    {editingId === cat.id ? (
                      <input
                        type="text"
                        value={editingName}
                        onChange={(e) => setEditingName(e.target.value)}
                        className="border rounded px-2 py-1 w-full"
                      />
                    ) : (
                      cat.name
                    )}
                  </td>
                  <td className="py-2 px-4 border-b text-center space-x-2">
                    {editingId === cat.id ? (
                      <>
                        <button
                          onClick={handleSaveEdit}
                          className="bg-green-500 hover:bg-green-600 text-white px-3 py-1 rounded"
                        >
                          Save
                        </button>
                        <button
                          onClick={handleCancelEdit}
                          className="bg-gray-400 hover:bg-gray-500 text-white px-3 py-1 rounded"
                        >
                          Cancel
                        </button>
                      </>
                    ) : (
                      <>
                        <button
                          onClick={() => handleEditClick(cat)}
                          className="bg-yellow-400 hover:bg-yellow-500 text-white px-3 py-1 rounded"
                        >
                          Edit
                        </button>
                        <button
                          onClick={() => handleDelete(cat.id)}
                          className="bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded"
                        >
                          Delete
                        </button>
                      </>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>

          {categories.length === 0 && (
            <div className="text-center text-gray-500 mt-4">
              No categories available.
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default CategoryPage;
