import React, { useState, useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchTasks, addTask, updateTask } from '../actions/taskActions';
import { fetchCategories } from '../actions/categoryActions';
import { getAllUsers } from '../actions/authActions';
import TaskList from '../components/Task/TaskList';
import TaskForm from '../components/Task/TaskForm';

const Dashboard = () => {
  const dispatch = useDispatch();
  const allTasks = useSelector((state) => state.tasks);
  const categories = useSelector((state) => state.categories);
  const users = useSelector((state) => state.auth.users);
  const [showAddTaskForm, setShowAddTaskForm] = useState(false);
  const [selectedTask, setSelectedTask] = useState(null);

  useEffect(() => {
    dispatch(fetchTasks());
    dispatch(fetchCategories());
    dispatch(getAllUsers());
  }, [dispatch]);

  const handleAddTask = async (taskData) => {
    if (selectedTask) {
      await dispatch(updateTask(selectedTask.id, taskData));
    } else {
      await dispatch(addTask(taskData));
    }
    setShowAddTaskForm(false);
    setSelectedTask(null);
  };

  const handleEditTask = (task) => {
    setSelectedTask(task);
    setShowAddTaskForm(true);
  };

  return (
    <div className="min-h-screen bg-gray-100 p-4">
      <div className="container mx-auto">

        <div className="mb-6">
          <button
            className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
            onClick={() => {
              setSelectedTask(null);
              setShowAddTaskForm(true);
            }}
          >
            Add New Task
          </button>
        </div>

        <TaskList tasks={allTasks} onEdit={handleEditTask} />

        {showAddTaskForm && (
          <TaskForm
            onClose={() => {
              setShowAddTaskForm(false);
              setSelectedTask(null);
            }}
            onSave={handleAddTask}
            categories={categories}
            users={users}
            task={selectedTask} 
          />
        )}
      </div>
    </div>
  );
};

export default Dashboard;
