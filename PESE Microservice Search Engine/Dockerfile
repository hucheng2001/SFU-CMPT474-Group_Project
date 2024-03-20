# Use the slim version of the node 14 image as our base
FROM node:14-slim

# Create a directory for our application in the container 
RUN mkdir -p /usr/src/app

# Set this new directory as our working directory for subsequent instructions
WORKDIR /usr/src/app

# Copy all files in the current directory into the container
COPY . .

# Set the environment variable for the application's port
ENV PORT 4002

# Install all the node modules required by the API
RUN npm install

# Serve the 'build' directory on port 4200 using 'serve'
CMD ["npm", "start"]