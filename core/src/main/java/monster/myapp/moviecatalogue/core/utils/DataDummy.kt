package monster.myapp.moviecatalogue.core.utils

import monster.myapp.moviecatalogue.core.data.source.local.entity.TvShowEntity
import monster.myapp.moviecatalogue.core.domain.model.Catalogue
import java.util.*

/**
 * Created by robby on 05/05/21.
 */
object DataDummy {
    fun generateDummyMovies(): List<Catalogue> {
        val movies = ArrayList<Catalogue>()
        movies.add(
            Catalogue(
                460465,
                7.8,
                "Mortal Kombat",
                "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe.",
                "2021-04-07",
                "/xGuOF1T3WmPsAcQEQJfnG7Ud9f8.jpg",
                "/9yBVqNruk6Ykrwc32qrK2TIE5xw.jpg"
            )
        )
        movies.add(
            Catalogue(
                399566,
                8.2,
                "Godzilla vs. Kong",
                "In a time when monsters walk the Earth, humanity’s fight for its future sets Godzilla and Kong on a collision course that will see the two most powerful forces of nature on the planet collide in a spectacular battle for the ages.",
                "2021-03-24",
                "/pgqgaUx1cJb5oZQQ5v0tNARCeBp.jpg",
                "/inJjDhCjfhh3RtrJWBmmDqeuSYC.jpg"
            )
        )
        movies.add(
            Catalogue(
                804435,
                6.0,
                "Vanquish",
                "Victoria is a young mother trying to put her dark past as a Russian drug courier behind her, but retired cop Damon forces Victoria to do his bidding by holding her daughter hostage. Now, Victoria must use guns, guts and a motorcycle to take out a series of violent gangsters—or she may never see her child again.",
                "2021-04-16",
                "/AoWY1gkcNzabh229Icboa1Ff0BM.jpg",
                "/mYM8x2Atv4MaLulaV0KVJWI1Djv.jpg"
            )
        )
        movies.add(
            Catalogue(
                615457,
                8.5,
                "Nobody",
                "Hutch Mansell, a suburban dad, overlooked husband, nothing neighbor — a \"nobody.\" When two thieves break into his home one night, Hutch's unknown long-simmering rage is ignited and propels him on a brutal path that will uncover dark secrets he fought to leave behind.",
                "2021-03-26",
                "/oBgWY00bEFeZ9N25wWVyuQddbAo.jpg",
                "/6zbKgwgaaCyyBXE4Sun4oWQfQmi.jpg"
            )
        )
        movies.add(
            Catalogue(
                635302,
                8.3,
                "Demon Slayer -Kimetsu no Yaiba- The Movie: Mugen Train",
                "Tanjirō Kamado, joined with Inosuke Hashibira, a boy raised by boars who wears a boar's head, and Zenitsu Agatsuma, a scared boy who reveals his true power when he sleeps, boards the Infinity Train on a new mission with the Fire Hashira, Kyōjurō Rengoku, to defeat a demon who has been tormenting the people and killing the demon slayers who oppose it!",
                "2020-10-16",
                "/yF45egpHwaYLn4jTyZAgk0Cmug9.jpg",
                "/3FVe3OAdgz060JaxIAaUl5lo6cx.jpg"
            )
        )
        movies.add(
            Catalogue(
                632357,
                5.7,
                "The Unholy",
                "Alice, a young hearing-impaired girl who, after a supposed visitation from the Virgin Mary, is inexplicably able to hear, speak and heal the sick. As word spreads and people from near and far flock to witness her miracles, a disgraced journalist hoping to revive his career visits the small New England town to investigate. When terrifying events begin to happen all around, he starts to question if these phenomena are the works of the Virgin Mary or something much more sinister.",
                "2021-03-31",
                "/b4gYVcl8pParX8AjkN90iQrWrWO.jpg",
                "/zDq2pwPyt4xwSFHKUoNN2LohDWj.jpg"
            )
        )
        movies.add(
            Catalogue(
                791373,
                8.5,
                "Zack Snyder's Justice League",
                "Determined to ensure Superman's ultimate sacrifice was not in vain, Bruce Wayne aligns forces with Diana Prince with plans to recruit a team of metahumans to protect the world from an approaching threat of catastrophic proportions.",
                "2021-03-18",
                "/tnAuB8q5vv7Ax9UAEje5Xi4BXik.jpg",
                "/pcDc2WJAYGJTTvRSEIpRZwM3Ola.jpg"
            )
        )
        movies.add(
            Catalogue(
                726684,
                7.8,
                "Miraculous World: Shanghai – The Legend of Ladydragon",
                "To join Adrien in Shanghai, Marinette is going to visit her uncle Wang who is celebrating his anniversary. But, as soon as she arrives in China, her purse gets stolen with Tikki inside, whom she needs to secretly transform into Ladybug! Without money and alone in the immense city, Marinette accepts the help of a young and resourceful girl, Fei. The two girls will ally and discover the existence of a new magical jewel, the Prodigious. Hawk Moth, also present in Shanghai, seeks to finding it since a long time...",
                "2021-04-04",
                "/xt2EwFW5cxcmbDnVmH8izSftUtE.jpg",
                "/lHhc60NXYzswU4TvKSo45nY9Jzs.jpg"
            )
        )
        movies.add(
            Catalogue(
                615678,
                5.8,
                "Thunder Force",
                "In a world where supervillains are commonplace, two estranged childhood best friends reunite after one devises a treatment that gives them powers to protect their city.",
                "2021-04-09",
                "/duK11VQd4UPDa7UJrgrGx90xJOx.jpg",
                "/z7HLq35df6ZpRxdMAE0qE3Ge4SJ.jpg"
            )
        )
        movies.add(
            Catalogue(
                634528,
                7.5,
                "The Marksman",
                "Jim Hanson’s quiet life is suddenly disturbed by two people crossing the US/Mexico border – a woman and her young son – desperate to flee a Mexican cartel. After a shootout leaves the mother dead, Jim becomes the boy’s reluctant defender. He embraces his role as Miguel’s protector and will stop at nothing to get him to safety, as they go on the run from the relentless assassins.",
                "2021-01-15",
                "/6vcDalR50RWa309vBH1NLmG2rjQ.jpg",
                "/5Zv5KmgZzdIvXz2KC3n0MyecSNL.jpg"
            )
        )

        return movies
    }

    fun generateDummyTvShows(): List<TvShowEntity> {
        val tvShows = ArrayList<TvShowEntity>()

        tvShows.add(
            TvShowEntity(
                88396,
                7.9,
                "The Falcon and the Winter Soldier",
                "Following the events of “Avengers: Endgame”, the Falcon, Sam Wilson and the Winter Soldier, Bucky Barnes team up in a global adventure that tests their abilities, and their patience.",
                "2021-03-19",
                "/6kbAMLteGO8yyewYau6bJ683sw7.jpg",
                "/b0WmHGc8LHTdGCVzxRb3IBMur57.jpg"
            )
        )
        tvShows.add(
            TvShowEntity(
                95557,
                8.9,
                "Invincible",
                "Mark Grayson is a normal teenager except for the fact that his father is the most powerful superhero on the planet. Shortly after his seventeenth birthday, Mark begins to develop powers of his own and enters into his father’s tutelage.",
                "2021-03-26",
                "/yDWJYRAwMNKbIYT8ZB33qy84uzO.jpg",
                "/6UH52Fmau8RPsMAbQbjwN3wJSCj.jpg"
            )
        )
        tvShows.add(
            TvShowEntity(
                71712,
                8.6,
                "The Good Doctor",
                "A young surgeon with Savant syndrome is recruited into the surgical unit of a prestigious hospital. The question will arise: can a person who doesn't have the ability to relate to people actually save their lives.",
                "2017-09-25",
                "/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg",
                "/mZjZgY6ObiKtVuKVDrnS9VnuNlE.jpg"
            )
        )
        tvShows.add(
            TvShowEntity(
                60735,
                7.7,
                "The Flash",
                "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \\\"meta-human\\\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash.",
                "2014-10-07",
                "/lJA2RCMfsWoskqlQhXPSLFQGXEJ.jpg",
                "/z59kJfcElR9eHO9rJbWp4qWMuee.jpg"
            )
        )
        tvShows.add(
            TvShowEntity(
                79008,
                8.1,
                "Luis Miguel: The Series",
                "The series dramatizes the life story of Mexican superstar singer Luis Miguel, who has captivated audiences in Latin America and beyond for decades.",
                "2018-04-22",
                "/34FaY8qpjBAVysSfrJ1l7nrAQaD.jpg",
                "/wkyzeBBKLhSg1Oqhky5yoiFF2hG.jpg"
            )
        )
        tvShows.add(
            TvShowEntity(
                1416,
                8.2,
                "Grey's Anatomy",
                "Follows the personal and professional lives of a group of doctors at Seattle’s Grey Sloan Memorial Hospital.",
                "2005-03-27",
                "/clnyhPqj1SNgpAdeSS6a6fwE6Bo.jpg",
                "/edmk8xjGBsYVIf4QtLY9WMaMcXZ.jpg"
            )
        )
        tvShows.add(
            TvShowEntity(
                120587,
                7.5,
                "Haunted: Latin America",
                "Real people's terrifying tales of the chilling, unexplained and paranormal come to life with dramatic reenactments in this reality series.",
                "2021-03-31",
                "/Q1ZYG3kDS8iVIHOYOJ9NQmV0q7.jpg",
                "/lEZLrd3N9nIk5fnCL30GsboCEmB.jpg"
            )
        )
        tvShows.add(
            TvShowEntity(
                63174,
                8.5,
                "Lucifer",
                "Bored and unhappy as the Lord of Hell, Lucifer Morningstar abandoned his throne and retired to Los Angeles, where he has teamed up with LAPD detective Chloe Decker to take down criminals. But the longer he's away from the underworld, the greater the threat that the worst of humanity could escape.",
                "2016-01-25",
                "/4EYPN5mVIhKLfxGruy7Dy41dTVn.jpg",
                "/ta5oblpMlEcIPIS2YGcq9XEkWK2.jpg"
            )
        )
        tvShows.add(
            TvShowEntity(
                69050,
                8.6,
                "Riverdale",
                "Set in the present, the series offers a bold, subversive take on Archie, Betty, Veronica and their friends, exploring the surreality of small-town life, the darkness and weirdness bubbling beneath Riverdale’s wholesome facade.",
                "2017-01-26",
                "/wRbjVBdDo5qHAEOVYoMWpM58FSA.jpg",
                "/qZtAf4Z1lazGQoYVXiHOrvLr5lI.jpg"
            )
        )
        tvShows.add(
            TvShowEntity(
                65820,
                6.9,
                "Van Helsing",
                "Vanessa Helsing, the daughter of famous vampire hunter and Dracula nemesis Abraham Van Helsing is resurrected five years in the future to find out that vampires have taken over the world and that she possesses unique power over them. She is humanity’s last hope to lead an offensive to take back what has been lost.",
                "2016-09-23",
                "/r8ODGmfNbZQlNhiJl2xQENE2jsk.jpg",
                "/5VltHQJXdmbSD6gEJw3R8R1Kbmc.jpg"
            )
        )

        return tvShows
    }
}