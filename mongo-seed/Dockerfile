FROM mongo
ADD init.json /init.json
CMD mongoimport --verbose --drop --host mongo-db --username root --password "example" --authenticationDatabase admin --db learning_assistant --collection countries --type json --file /init.json --jsonArray
