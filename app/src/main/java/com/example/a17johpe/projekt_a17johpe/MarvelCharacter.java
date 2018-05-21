package com.example.a17johpe.projekt_a17johpe;

/**
 * Created by a17johpe on 2018-05-18.
 */

public class MarvelCharacter {
    private String name;
    private String team;
    private String firstAppearance;
    private String homeLocation;
    private String heroName;
    private String actor;
    private String wikipage;
    private String image;

    public MarvelCharacter (String inName, String inTeam, String inFirstAppearance, String inHomeLocation,
                            String inHeroName, String inActor, String inWikipage, String inImage) {
        name = inName;
        team = inTeam;
        firstAppearance = inFirstAppearance;
        homeLocation = inHomeLocation;
        heroName = inHeroName;
        actor = inActor;
        wikipage = inWikipage;
        image = inImage;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public String getHeroName() {
        return heroName;
    }

    public String getTeam() {
        return team;
    }

    public String getImage() {
        return image;
    }

    public String getInfo() {
        String str = name + " " + team + " " + firstAppearance + " " + homeLocation + " " + heroName + " " + actor + " " + wikipage + " " + image;
        return str;
    }
}
