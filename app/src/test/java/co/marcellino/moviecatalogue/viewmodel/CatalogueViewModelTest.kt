package co.marcellino.moviecatalogue.viewmodel

import co.marcellino.moviecatalogue.model.Movie
import co.marcellino.moviecatalogue.model.Show
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CatalogueViewModelTest {

    private lateinit var viewModel: CatalogueViewModel

    @Before
    fun setUp() {
        viewModel = CatalogueViewModel()
    }

    @Test
    fun isMoviesListInitialized() {
        assertEquals(false, viewModel.isMoviesListInitialized())

        val expectedMoviesList = listOf(
            Movie(
                "A Star Is Born",
                "2018",
                "https://m.media-amazon.com/images/M/MV5BNmE5ZmE3OGItNTdlNC00YmMxLWEzNjctYzAwOGQ5ODg0OTI0XkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg",
                "7.7/10",
                "136 min",
                "Drama, Music, Romance",
                "A musician helps a young singer find fame as age and alcoholism send his own career into a downward spiral.",
                "Bradley Cooper",
                "Eric Roth (screenplay by), Bradley Cooper (screenplay by), Will Fetters (screenplay by), Moss Hart (based on the 1954 screenplay by), John Gregory Dunne (based on the 1976 screenplay by), Joan Didion (based on the 1976 screenplay by), Frank Pierson (based on the 1976 screenplay by), William A. Wellman (based on a story by), Robert Carson (based on a story by)",
                "Lady Gaga, Bradley Cooper, Sam Elliott, Andrew Dice Clay",
                "Won 1 Oscar. Another 94 wins & 274 nominations."
            )
        )

        val dummyInputStream =
            "{\"Movies\":[{\"Title\":\"A Star Is Born\",\"Year\":\"2018\",\"Rated\":\"R\",\"Released\":\"05 Oct 2018\",\"Runtime\":\"136 min\",\"Genre\":\"Drama, Music, Romance\",\"Director\":\"Bradley Cooper\",\"Writer\":\"Eric Roth (screenplay by), Bradley Cooper (screenplay by), Will Fetters (screenplay by), Moss Hart (based on the 1954 screenplay by), John Gregory Dunne (based on the 1976 screenplay by), Joan Didion (based on the 1976 screenplay by), Frank Pierson (based on the 1976 screenplay by), William A. Wellman (based on a story by), Robert Carson (based on a story by)\",\"Actors\":\"Lady Gaga, Bradley Cooper, Sam Elliott, Andrew Dice Clay\",\"Plot\":\"A musician helps a young singer find fame as age and alcoholism send his own career into a downward spiral.\",\"Language\":\"English\",\"Country\":\"USA\",\"Awards\":\"Won 1 Oscar. Another 94 wins & 274 nominations.\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNmE5ZmE3OGItNTdlNC00YmMxLWEzNjctYzAwOGQ5ODg0OTI0XkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"7.7/10\"},{\"Source\":\"Rotten Tomatoes\",\"Value\":\"90%\"},{\"Source\":\"Metacritic\",\"Value\":\"88/100\"}],\"Metascore\":\"88\",\"imdbRating\":\"7.7\",\"imdbVotes\":\"324,024\",\"imdbID\":\"tt1517451\",\"Type\":\"movie\",\"DVD\":\"N/A\",\"BoxOffice\":\"N/A\",\"Production\":\"Jon Peters/Bill Gerber/Joint Effort Production\",\"Website\":\"N/A\",\"Response\":\"True\"}]}"
                .byteInputStream()
        viewModel.getMoviesList(dummyInputStream)

        assert(viewModel.isMoviesListInitialized())
        assertEquals(expectedMoviesList, viewModel.moviesList)
    }

    @Test
    fun isShowsListInitialized() {
        assertEquals(false, viewModel.isShowsListInitialized())

        val expectedShowsList = listOf(
            Show(
                "Sherlock",
                "2010–2017",
                "https://m.media-amazon.com/images/M/MV5BMWY3NTljMjEtYzRiMi00NWM2LTkzNjItZTVmZjE0MTdjMjJhL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyNTQ4NTc5OTU@._V1_SX300.jpg",
                "9.1/10",
                "88 min",
                "Crime, Drama, Mystery, Thriller",
                "A modern update finds the famous sleuth and his doctor partner solving crime in 21st century London.",
                "Mark Gatiss, Steven Moffat",
                "Benedict Cumberbatch, Martin Freeman, Una Stubbs, Rupert Graves",
                "Nominated for 1 Golden Globe. Another 89 wins & 173 nominations."
            )
        )

        val dummyInputStream =
            "{\"TVShows\":[{\"Title\":\"Sherlock\",\"Year\":\"2010–2017\",\"Rated\":\"TV-14\",\"Released\":\"24 Oct 2010\",\"Runtime\":\"88 min\",\"Genre\":\"Crime, Drama, Mystery, Thriller\",\"Director\":\"N/A\",\"Writer\":\"Mark Gatiss, Steven Moffat\",\"Actors\":\"Benedict Cumberbatch, Martin Freeman, Una Stubbs, Rupert Graves\",\"Plot\":\"A modern update finds the famous sleuth and his doctor partner solving crime in 21st century London.\",\"Language\":\"English\",\"Country\":\"UK, USA\",\"Awards\":\"Nominated for 1 Golden Globe. Another 89 wins & 173 nominations.\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BMWY3NTljMjEtYzRiMi00NWM2LTkzNjItZTVmZjE0MTdjMjJhL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyNTQ4NTc5OTU@._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"9.1/10\"}],\"Metascore\":\"N/A\",\"imdbRating\":\"9.1\",\"imdbVotes\":\"781,512\",\"imdbID\":\"tt1475582\",\"Type\":\"series\",\"totalSeasons\":\"4\",\"Response\":\"True\"}]}"
                .byteInputStream()
        viewModel.getShowsList(dummyInputStream)

        assert(viewModel.isShowsListInitialized())
        assertEquals(expectedShowsList, viewModel.showsList)
    }
}
