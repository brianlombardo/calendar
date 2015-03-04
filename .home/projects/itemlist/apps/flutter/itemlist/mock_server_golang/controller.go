package main

import (
	"encoding/json"
	"fmt"
	"github.com/google/uuid"
	"github.com/gorilla/mux"
	"io/ioutil"
	"log"
	"net/http"
	"os"
	"strconv"
	"time"
)

func wait() {
	delay, err := strconv.ParseInt(os.Args[1], 10, 0)
	if err == nil {
		time.Sleep(time.Duration(delay) * time.Second)
	}
}

func GetItems(w http.ResponseWriter, _ *http.Request) {
	wait()

	items := FindAll()

	bytes, err := json.Marshal(items)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}

	writeJsonResponse(w, bytes)
}

func GetItem(w http.ResponseWriter, r *http.Request) {
	wait()

	vars := mux.Vars(r)
	id := vars["id"]

	item, ok := FindBy(id)
	if !ok {
		http.NotFound(w, r)
		return
	}

	bytes, err := json.Marshal(item)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}

	writeJsonResponse(w, bytes)
}

func CreateItem(w http.ResponseWriter, r *http.Request) {
	wait()

	body, err := ioutil.ReadAll(r.Body)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}

	item := new(Item)
	err = json.Unmarshal(body, item)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}

	item.ID = fmt.Sprintf("%d%s", int32(time.Now().Unix()), uuid.New().String()[0:7])
	Save(item.ID, item)

	w.Header().Set("Location", r.URL.Path+"/"+item.ID)
	w.WriteHeader(http.StatusCreated)
}

func UpdateItem(w http.ResponseWriter, r *http.Request) {
	wait()

	vars := mux.Vars(r)
	id := vars["id"]

	body, err := ioutil.ReadAll(r.Body)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}

	com := new(Item)
	err = json.Unmarshal(body, com)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}

	Save(id, com)
}

func DeleteItem(w http.ResponseWriter, r *http.Request) {
	wait()

	vars := mux.Vars(r)
	id := vars["id"]

	Remove(id)
	w.WriteHeader(http.StatusNoContent)
}

func writeJsonResponse(w http.ResponseWriter, bytes []byte) {
	w.Header().Set("Content-Type", "application/json; charset=UTF-8")
	_, err := w.Write(bytes)
	if err != nil {
		log.Print("failed to write Json response")
	}
}
