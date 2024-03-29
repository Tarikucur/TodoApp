# TodoApp

This backend service uses BCrypt password encryption with bearer token authentication.

## Running the Backend Service

To run the backend service, you have two options:

### Option 1: Use Docker Image (Recommended)

1. Pull the Docker image from [tarikucur/todoapp](https://hub.docker.com/r/tarikucur/todoapp) with the tag "latest":

docker pull tarikucur/todoapp:latest

2. Run the Docker image, setting the `MONGODB_URI` environment variable with your MongoDB Atlas URI:

docker run -e MONGODB_URI="your_mongodb_atlas_uri" -it -p 8080:8080 tarikucur/todoapp:latest

Note: Make sure to replace `"your_mongodb_atlas_uri"` with your MongoDB Atlas URI.

### Option 2: Build Your Own Docker Image

1. Build the project using the following command in the root folder:

mvn clean install -DskipTests

2. Build the Docker image:

docker build . -t "imagename"

Replace `"imagename"` with the desired name for your Docker image.

3. Run the Docker image, setting the `MONGODB_URI` environment variable:

docker run -e MONGODB_URI="your_mongodb_atlas_uri" -it -p 8080:8080 "imagename"

## MongoDB Atlas

If you have your own MongoDB Atlas account, create a database and use its URI.

If you don't have a MongoDB Atlas account, contact me, and I can provide you with my own database URI.

Feel free to choose the option that best fits your needs!

## Testing the Endpoints With Postman

You can import the collection file named "TodoApp Collection.postman_collection" which is in the root folder.

If you want to pre-defined tests, just run the collection as it is. You can change the values in the request fields as well.

If you need to run the requests manually, you need to create an environment and set it as active for the collection so that the values can be saved.

