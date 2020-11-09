package co.marcellino.moviecatalogue.utils

import android.content.Context
import android.content.res.Resources
import androidx.test.core.app.ApplicationProvider
import co.marcellino.moviecatalogue.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class DataParserTest {

    private lateinit var resources: Resources

    @Before
    fun setUp() {
        resources = ApplicationProvider.getApplicationContext<Context>().resources
    }

    @Test
    fun getMoviesList() {
        assertNotNull(resources)

        // Take index 0-4.
        val expectedListString =
            "[Movie(title=A Star Is Born, year=2018, posterPath=https://m.media-amazon.com/images/M/MV5BNmE5ZmE3OGItNTdlNC00YmMxLWEzNjctYzAwOGQ5ODg0OTI0XkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg, rating=7.7/10, runtime=136 min, genre=Drama, Music, Romance, plot=A musician helps a young singer find fame as age and alcoholism send his own career into a downward spiral., director=Bradley Cooper, writer=Eric Roth (screenplay by), Bradley Cooper (screenplay by), Will Fetters (screenplay by), Moss Hart (based on the 1954 screenplay by), John Gregory Dunne (based on the 1976 screenplay by), Joan Didion (based on the 1976 screenplay by), Frank Pierson (based on the 1976 screenplay by), William A. Wellman (based on a story by), Robert Carson (based on a story by), actors=Lady Gaga, Bradley Cooper, Sam Elliott, Andrew Dice Clay, awards=Won 1 Oscar. Another 94 wins & 274 nominations.), Movie(title=Alita: Battle Angel, year=2019, posterPath=https://m.media-amazon.com/images/M/MV5BMTQzYWYwYjctY2JhZS00NTYzLTllM2UtZWY5ZTk0NmYwYzIyXkEyXkFqcGdeQXVyMzgxODM4NjM@._V1_SX300.jpg, rating=7.3/10, runtime=122 min, genre=Action, Adventure, Sci-Fi, Thriller, plot=A deactivated cyborg's revived, but can't remember anything of her past and goes on a quest to find out who she is., director=Robert Rodriguez, writer=James Cameron (screenplay by), Laeta Kalogridis (screenplay by), Yukito Kishiro (based on the graphic novel series \"Gunnm\" by), actors=Rosa Salazar, Christoph Waltz, Jennifer Connelly, Mahershala Ali, awards=9 wins & 25 nominations.), Movie(title=Aquaman, year=2018, posterPath=https://m.media-amazon.com/images/M/MV5BOTk5ODg0OTU5M15BMl5BanBnXkFtZTgwMDQ3MDY3NjM@._V1_SX300.jpg, rating=6.9/10, runtime=143 min, genre=Action, Adventure, Fantasy, Sci-Fi, plot=Arthur Curry, the human-born heir to the underwater kingdom of Atlantis, goes on a quest to prevent a war between the worlds of ocean and land., director=James Wan, writer=David Leslie Johnson-McGoldrick (screenplay by), Will Beall (screenplay by), Geoff Johns (story by), James Wan (story by), Will Beall (story by), Paul Norris (Aquaman created by), Mort Weisinger (Aquaman created by), actors=Jason Momoa, Amber Heard, Willem Dafoe, Patrick Wilson, awards=2 wins & 38 nominations.), Movie(title=Bohemian Rhapsody, year=2018, posterPath=https://m.media-amazon.com/images/M/MV5BMTA2NDc3Njg5NDVeQTJeQWpwZ15BbWU4MDc1NDcxNTUz._V1_SX300.jpg, rating=8.0/10, runtime=134 min, genre=Biography, Drama, Music, plot=The story of the legendary British rock band Queen and lead singer Freddie Mercury, leading up to their famous performance at Live Aid (1985)., director=Bryan Singer, writer=Anthony McCarten (story by), Peter Morgan (story by), Anthony McCarten (screenplay by), actors=Rami Malek, Lucy Boynton, Gwilym Lee, Ben Hardy, awards=Won 4 Oscars. Another 41 wins & 76 nominations.), Movie(title=Fantastic Beasts: The Crimes of Grindelwald, year=2018, posterPath=https://m.media-amazon.com/images/M/MV5BYWVlMDI5N2UtZTIyMC00NjZkLWI5Y2QtODM5NGE5MzA0NmVjXkEyXkFqcGdeQXVyNzU3NjUxMzE@._V1_SX300.jpg, rating=6.6/10, runtime=134 min, genre=Adventure, Family, Fantasy, plot=The second installment of the \"Fantastic Beasts\" series featuring the adventures of Magizoologist Newt Scamander., director=David Yates, writer=J.K. Rowling, J.K. Rowling (based upon characters created by), actors=Johnny Depp, Kevin Guthrie, Carmen Ejogo, Wolf Roth, awards=Nominated for 2 BAFTA Film Awards. Another 3 wins & 18 nominations.)]"

        val actualList =
            DataParser.getMoviesList(RawReader.readFromRaw(resources.openRawResource(R.raw.movies)))

        assertNotNull(actualList)
        assertEquals(12, actualList.size)
        assertEquals(expectedListString, actualList.subList(0, 5).toString())
    }

    @Test
    fun getShowsList() {
        assertNotNull(resources)

        // Take index 0-4.
        val expectedListString =
            "[Show(title=Sherlock, year=2010–2017, posterPath=https://m.media-amazon.com/images/M/MV5BMWY3NTljMjEtYzRiMi00NWM2LTkzNjItZTVmZjE0MTdjMjJhL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyNTQ4NTc5OTU@._V1_SX300.jpg, rating=9.1/10, runtime=88 min, genre=Crime, Drama, Mystery, Thriller, plot=A modern update finds the famous sleuth and his doctor partner solving crime in 21st century London., writer=Mark Gatiss, Steven Moffat, actors=Benedict Cumberbatch, Martin Freeman, Una Stubbs, Rupert Graves, awards=Nominated for 1 Golden Globe. Another 89 wins & 173 nominations.), Show(title=Breaking Bad, year=2008–2013, posterPath=https://m.media-amazon.com/images/M/MV5BMjhiMzgxZTctNDc1Ni00OTIxLTlhMTYtZTA3ZWFkODRkNmE2XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg, rating=9.5/10, runtime=49 min, genre=Crime, Drama, Thriller, plot=A high school chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine in order to secure his family's future., writer=Vince Gilligan, actors=Bryan Cranston, Anna Gunn, Aaron Paul, Betsy Brandt, awards=Won 2 Golden Globes. Another 151 wins & 238 nominations.), Show(title=Game of Thrones, year=2011–2019, posterPath=https://m.media-amazon.com/images/M/MV5BYTRiNDQwYzAtMzVlZS00NTI5LWJjYjUtMzkwNTUzMWMxZTllXkEyXkFqcGdeQXVyNDIzMzcwNjc@._V1_SX300.jpg, rating=9.3/10, runtime=57 min, genre=Action, Adventure, Drama, Fantasy, Romance, plot=Nine noble families fight for control over the lands of Westeros, while an ancient enemy returns after being dormant for millennia., writer=David Benioff, D.B. Weiss, actors=Peter Dinklage, Lena Headey, Emilia Clarke, Kit Harington, awards=Won 1 Golden Globe. Another 374 wins & 602 nominations.), Show(title=Rick and Morty, year=2013–, posterPath=https://m.media-amazon.com/images/M/MV5BZjRjOTFkOTktZWUzMi00YzMyLThkMmYtMjEwNmQyNzliYTNmXkEyXkFqcGdeQXVyNzQ1ODk3MTQ@._V1_SX300.jpg, rating=9.2/10, runtime=23 min, genre=Animation, Adventure, Comedy, Sci-Fi, plot=An animated series that follows the exploits of a super scientist and his not-so-bright grandson., writer=Dan Harmon, Justin Roiland, actors=Justin Roiland, Chris Parnell, Spencer Grammer, Sarah Chalke, awards=Won 2 Primetime Emmys. Another 15 wins & 17 nominations.), Show(title=The Wire, year=2002–2008, posterPath=https://m.media-amazon.com/images/M/MV5BZmY5ZDMxODEtNWIwOS00NjdkLTkyMjktNWRjMDhmYjJjN2RmXkEyXkFqcGdeQXVyNTA4NzY1MzY@._V1_SX300.jpg, rating=9.3/10, runtime=59 min, genre=Crime, Drama, Thriller, plot=The Baltimore drug scene, as seen through the eyes of drug dealers and law enforcement., writer=David Simon, actors=Dominic West, John Doman, Deirdre Lovejoy, Wendell Pierce, awards=Nominated for 2 Primetime Emmys. Another 16 wins & 52 nominations.)]"

        val actualList =
            DataParser.getShowsList(RawReader.readFromRaw(resources.openRawResource(R.raw.shows)))

        assertNotNull(actualList)
        assertEquals(12, actualList.size)
        assertEquals(expectedListString, actualList.subList(0, 5).toString())
    }
}