package org.example.entities;

import java.util.ArrayList;
import java.util.SortedSet;

//Class used to handle the game accounts
public class Account {
    ArrayList<org.example.entities.characters.Character> characters ;
    int numberofgames;
    Information information;

    public Account(ArrayList<org.example.entities.characters.Character> characters, int gamesNumber, Information information) {
        this.characters = characters;
        this.numberofgames = gamesNumber;
        this.information = information;
    }

    public int getNumberofgames() {
        return this.numberofgames;
    }

    public ArrayList<org.example.entities.characters.Character> getCharacters() {
        return this.characters;
    }

    public Information getInformation() {
        return this.information;
    }

    public void setNumberofgames(int numberofgames) {
        this.numberofgames = numberofgames;
    }

    public void setCharacters(ArrayList<org.example.entities.characters.Character> characters) {
        this.characters = characters;
    }
    public void setInformation(Information information) {
        this.information = information;
    }

    public void addCharacter(org.example.entities.characters.Character character) {
        this.characters.add(character);
    }

    public void removeCharacter(org.example.entities.characters.Character character) {
        this.characters.remove(character);
    }

    //Internal class that handles the information related to each account
    public static class Information {
        private Credentials creds;
        private SortedSet<String> favGames;
        private String name;
        private String country;

        private Information(InformationBuilder builder) {
            this.creds = builder.creds;
            this.favGames = builder.favGames;
            this.name = builder.name;
            this.country = builder.country;
        }

        public Credentials getCreds() {
            return creds;
        }

        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }

        public void setCreds(Credentials creds) {
            this.creds = creds;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public SortedSet<String> getFavGames() {
            return favGames;
        }

        public void setFavGames(SortedSet<String> favGames) {
            this.favGames = favGames;
        }

        public void addFavGame(String game) {
            this.favGames.add(game);
        }

        public void removeFavGame(String game) {
            this.favGames.remove(game);
        }

        @Override
        public String toString() {
            return "Account Info : {name - " + this.name + "} ; {country -" + this.country + "} ; {" + this.favGames + "} ." ;
        }

        public static class InformationBuilder {
            private Credentials creds;
            private SortedSet<String> favGames;
            private String name;
            private String country;

            public InformationBuilder creds(Credentials creds) {
                this.creds = creds;
                return this;
            }

            public InformationBuilder favGames(SortedSet<String> favGames) {
                this.favGames = favGames;
                return this;
            }

            public InformationBuilder name(String name) {
                this.name = name;
                return this;
            }

            public InformationBuilder country(String country) {
                this.country = country;
                return this;
            }

            public Information build() {
                return new Information(this);
            }

        }
    }

}
