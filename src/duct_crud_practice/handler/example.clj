(ns duct-crud-practice.handler.example
  (:use [hiccup.core])
  (:require [ataraxy.core :as ataraxy]
            [ataraxy.response :as response]
            [clojure.string :as str]
            [duct-crud-practice.boundary.users :as db.users]
            [integrant.core :as ig]))

(defmethod ig/init-key :duct-crud-practice.handler/example [_ options]
  (fn [{[_] :ataraxy/result}]
    [::response/ok (html [:span "This is an example handler"])]))

(defn user-form [action method user]
  [:form {:action action :method "post"}
   [:input {:name "_method" :value method :type "hidden"}]
   [:div
    [:label {:for "name"} "name"]
    [:input {:name "name" :value (:name user)}]]
   [:div
    [:label {:for "email"} "email"]
    [:input {:name "email" :value (:email user)}]]
   [:button {:type "submit"} "Submit"]])

(defn show-users-view [users]
  (html [:div
         [:div "Users"]
         [:a {:href "/users/new"} "Add user"]
         [:table
          [:thead
           [:tr
            [:th "id"]
            [:th "name"]]]
          [:tbody
           (doall
            (for [user users]
              [:tr
               [:td [:a {:href (str "/users/" (:id user))} (:id user)]]
               [:td (:name user)]]))]]]))

(defn show-user-view [user]
  (html [:div "User"
         (pr-str user)
         [:div
          [:a {:href (str "/users/" (:id user) "/edit")} "edit"]
          [:span " "]
          [:a {:href (str "/users/" (:id user) "/delete")} "delete"]]]))

(defn new-user-view [user]
  (html [:div "New User"
         (user-form "/users/" "post" user)]))

(defn edit-user-view [user]
  (html [:div "Edit User"
         (user-form (str "/users/" (:id user) "/update") "put" user)]))

(defmethod ig/init-key :duct-crud-practice.handler/user-new [_ options]
  (fn [{[_] :ataraxy/result}]
    [::response/ok (new-user-view nil)]))

(defmethod ig/init-key :duct-crud-practice.handler/user-index [_ {:keys [db]}]
  (fn [{[_] :ataraxy/result}]
    (let [users (db.users/get-users db)]
      [::response/ok (show-users-view users)])))

(defmethod ig/init-key :duct-crud-practice.handler/user-create [_ {:keys [db]}]
  (fn [{[_ params] :ataraxy/result}]
    (if (not-empty (:name params))
      (let [user (first (db.users/create-user db params))]
        [::response/found (str "/users/" (:id user))])
      [::response/ok (new-user-view params)])))

(defmethod ig/init-key :duct-crud-practice.handler/user-show [_ {:keys [db]}]
  (fn [{[_ id] :ataraxy/result}]
    (let [user (first (db.users/get-user db id))]
      [::response/ok (show-user-view user)])))

(defmethod ig/init-key :duct-crud-practice.handler/user-edit [_ {:keys [db]}]
  (fn [{[_ id] :ataraxy/result}]
    (let [user (first (db.users/get-user db id))]
      [::response/ok (edit-user-view user)])))

(defmethod ig/init-key :duct-crud-practice.handler/user-update [_ {:keys [db]}]
  (fn [{[_ id params] :ataraxy/result}]
    (if (not-empty (:name params))
      (let [user (first (db.users/update-user db id params))]
        [::response/found (str "/users/" (:id user))])
      [::response/ok (edit-user-view params)])))

(defmethod ig/init-key :duct-crud-practice.handler/user-destroy [_ {:keys [db]}]
  (fn [{[_ id] :ataraxy/result}]
    (let [user (first (db.users/delete-user db id))]
      [::response/found "/users/"])))
