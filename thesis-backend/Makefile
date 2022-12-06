postgres:
	docker run --name postgres14-thesis -p 5432:5432 -e POSTGRES_USER=root -e POSTGRES_PASSWORD=secret -d postgres:14-alpine

createdb:
	docker exec -it postgres14-thesis createdb --username=root --owner=root thesis

dropdb:
	docker exec -it postgres14-thesis dropdb thesis

migrateup:
	migrate -path db/migration -database "postgresql://root:secret@localhost:5432/thesis?sslmode=disable" -verbose up

migratedown:
	migrate -path db/migration -database "postgresql://root:secret@localhost:5432/thesis?sslmode=disable" -verbose down

.PHONY: postgres createdb dropdb migrateup migratedown