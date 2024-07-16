const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const cors = require('cors');

const app = express();
const PORT = process.env.PORT || 5000;

// Middleware
app.use(cors());
app.use(bodyParser.json());

// MongoDB connection
mongoose.connect('mongodb+srv://sajangautam7090:So2EWZHtRBOjkivF@onlineide.363wtkb.mongodb.net/?retryWrites=true&w=majority&appName=onlineIDE', {
    useNewUrlParser: true,
    useUnifiedTopology: true
}).then(() => {
    console.log('Connected to MongoDB');
}).catch((error) => {
    console.error('MongoDB connection error:', error);
});

// Project schema and model
const projectSchema = new mongoose.Schema({
    projectName: String,
    code: String,
    language: String,
    email: String
});

const Project = mongoose.model('Project', projectSchema);

// Define route to save project
app.post('/api/saveProject', async (req, res) => {
    const { projectName, code, language, email } = req.body;

    try {
        // Check if the project already exists for the current user
        const existingProject = await Project.findOne({ projectName, email });

        if (existingProject) {
            // Delete the existing project
            await Project.findByIdAndDelete(existingProject._id);
        }

        // Save the new project
        const project = new Project({
            projectName,
            code,
            language,
            email
        });

        await project.save();
        res.json({ message: 'Project saved successfully' });
    } catch (error) {
        console.error('Error saving project:', error);
        res.status(500).json({ error: 'Failed to save project' });
    }
});


// Define route to get projects by email
app.get('/api/myprojects', async (req, res) => {
    const email = req.query.email; // Get email from query parameters

    try {
        const projects = await Project.find({ email }, 'projectName'); // Only select projectName
        
        if (!projects || projects.length === 0) {
            return res.status(404).json({ message: 'No projects found' });
        }

        res.json(projects);
    } catch (error) {
        console.error('Error fetching projects:', error);
        res.status(500).json({ error: 'Failed to fetch projects' });
    }
});

// Define route to get project by ID
app.get('/api/projects/:id', async (req, res) => {
    const { id } = req.params;

    try {
        const project = await Project.findById(id);

        if (!project) {
            return res.status(404).json({ message: 'Project not found' });
        }

        res.json(project);
    } catch (error) {
        console.error('Error fetching project:', error);
        res.status(500).json({ error: 'Failed to fetch project' });
    }
});

// Define route to delete a project by ID
app.delete('/api/projects/:id', async (req, res) => {
    const { id } = req.params;

    try {
        const project = await Project.findByIdAndDelete(id);

        if (!project) {
            return res.status(404).json({ message: 'Project not found' });
        }

        res.json({ message: 'Project deleted successfully' });
    } catch (error) {
        console.error('Error deleting project:', error);
        res.status(500).json({ error: 'Failed to delete project' });
    }
});

app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});
