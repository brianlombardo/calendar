package main

import (
	"github.com/gorilla/mux"
	"log"
	"net/http"
)

func main() {
	router := mux.NewRouter().StrictSlash(true)
	subRouter := router.PathPrefix("/api/v1").Subrouter()
	subRouter.Methods("GET").Path("/items").HandlerFunc(GetItems)
	subRouter.Methods("GET").Path("/items/{id}").HandlerFunc(GetItem)
	subRouter.Methods("POST").Path("/items").HandlerFunc(CreateItem)
	subRouter.Methods("PUT").Path("/items/{id}").HandlerFunc(UpdateItem)
	subRouter.Methods("DELETE").Path("/items/{id}").HandlerFunc(DeleteItem)

	log.Fatal(http.ListenAndServe(":3000", router))
}
