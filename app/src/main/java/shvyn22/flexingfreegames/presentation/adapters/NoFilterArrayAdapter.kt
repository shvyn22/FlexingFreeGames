package shvyn22.flexingfreegames.presentation.adapters

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter

/*
    Overrides filtering functionality of ArrayAdapter to solve the bug of losing dropdown list items
    (i.e. adapter filters suggestions based on the "frozen" text which is retained) after recreation.
    Corresponding issue: https://github.com/material-components/material-components-android/issues/1464
*/
class NoFilterArrayAdapter<T>(
    context: Context,
    resource: Int,
    objects: List<T>
) : ArrayAdapter<T>(context, resource, objects) {

    private val noOpFilter = object : Filter() {
        private val noOpResult = FilterResults()
        override fun performFiltering(constraint: CharSequence?) = noOpResult
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {}
    }

    override fun getFilter() = noOpFilter
}