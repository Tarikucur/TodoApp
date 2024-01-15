# TodoApp

To run the backend service, you can directly use the docker image in https://hub.docker.com/r/tarikucur/todoapp with the tag "latest".
For that reason, you should compile the following in order:

docker pull tarikucur/todoapp
docker run -e MONGODB_URI="your_mongodb_atlas_uri" -it -p 8080:8080 tarikucur/todoapp:latest
Note that the MONGODB_URI environment variable needs a URI before you run the image.
If you have your own mongodb atlas account, you can create a database for that reason.
If you do not have a mongodb atlas account, I can provide you with my own database uri 
information, just contact me!

You can also create your own docker image to run different versions of the application. First,
build the project by using the command:
"mvn clean install -DskipTests"
in the root folder.
Then, use the following command to build the image:
docker build . -t "imagename"
then, you can run the docker image like you did in the first option.
docker run -e MONGODB_URI="your_mongodb_atlas_uri" -it -p 8080:8080 "imagename"
