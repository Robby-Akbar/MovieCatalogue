package monster.myapp.moviecatalogue.core.ui

import monster.myapp.moviecatalogue.core.domain.model.Catalogue

/**
 * Created by robby on 06/05/21.
 */
interface ItemCatalogueCallback {
    fun onShareClick(catalogue: Catalogue)
    fun onItemClick(catalogue: Catalogue)
}