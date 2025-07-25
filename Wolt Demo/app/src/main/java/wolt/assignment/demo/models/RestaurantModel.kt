package wolt.assignment.demo.models

data class RestaurantModel(
    val created: Created?,
    val expires_in_seconds: Int?,
    val filtering: Filtering?,
    val how_search_works_link: HowSearchWorksLink?,
    val how_search_works_url: String?,
    val name: String?,
    val page_title: String?,
    val sections: List<Section>?,
    val show_large_title: Boolean?,
    val show_map: Boolean?,
    val sorting: Sorting?,
    val track_id: String?
) {
    data class Created(
        val `$date`: Long?
    )

    data class Filtering(
        val filters: List<Filter>?
    ) {
        data class Filter(
            val id: String?,
            val name: String?,
            val type: String?,
            val values: List<Value>?
        ) {
            data class Value(
                val id: String?,
                val name: String?
            )
        }
    }

    data class HowSearchWorksLink(
        val type: String?,
        val url: String?
    )

    data class Section(
        val content_id: String?,
        val content_type: String?,
        val end_of_section: EndOfSection?,
        val hide_delivery_info: Boolean?,
        val items: List<Item>?,
        val link: Link?,
        val name: String?,
        val template: String?,
        val title: String?
    ) {
        data class EndOfSection(
            val link: Link?,
            val type: String?
        ) {
            data class Link(
                val target: String?,
                val target_sort: String?,
                val target_title: String?,
                val telemetry_object_id: String?,
                val title: String?,
                val type: String?,
                val view_name: String?
            )
        }

        data class Item(
            val content_id: String?,
            val description: String?,
            val filtering: Filtering?,
            val image: Image?,
            val link: Link?,
            val overlay: String?,
            val overlay_v2: OverlayV2?,
            val quantity: Int?,
            val quantity_str: String?,
            val sorting: Sorting?,
            val telemetry_object_id: String?,
            val telemetry_venue_badges: List<String>?,
            val template: String?,
            val title: String?,
            val track_id: String?,
            val venue: Venue?
        ) {
            data class Filtering(
                val filters: List<Filter>?
            ) {
                data class Filter(
                    val id: String?,
                    val values: List<String>?
                )
            }

            data class Image(
                val blurhash: String?,
                val url: String?
            )

            data class Link(
                val selected_delivery_method: String?,
                val target: String?,
                val target_sort: String?,
                val target_title: String?,
                val title: String?,
                val type: String?,
                val venue_mainimage_blurhash: String?,
                val view_name: String?
            )

            data class OverlayV2(
                val icon: String?,
                val primary_text: String?,
                val secondary_text: String?,
                val telemetry_status: String?,
                val variant: String?
            )

            data class Sorting(
                val sortables: List<Sortable>?
            ) {
                data class Sortable(
                    val id: String?,
                    val value: Int?
                )
            }

            data class Venue(
                val address: String?,
                val badges: List<Badge>?,
                val badges_v2: List<Any>?,
                val brand_image: BrandImage?,
                val categories: List<Any>?,
                val city: String?,
                val country: String?,
                val currency: String?,
                val delivers: Boolean?,
                val delivery_highlight: Boolean?,
                val delivery_price_highlight: Boolean?,
                val estimate: Int?,
                val estimate_box: EstimateBox?,
                val estimate_range: String?,
                val franchise: String?,
                val id: String?,
                val location: List<Double>?,
                val name: String?,
                val online: Boolean?,
                val price_range: Int?,
                val product_line: String?,
                val promotions: List<Any>?,
                val rating: Rating?,
                val short_description: String?,
                val show_wolt_plus: Boolean?,
                val slug: String?,
                val tags: List<String>?,
                val venue_preview_items: List<VenuePreviewItem>?,
                var isFavorite: Boolean = false
            ) {
                data class Badge(
                    val text: String?,
                    val variant: String?
                )

                data class BrandImage(
                    val blurhash: String?,
                    val url: String?
                )

                data class EstimateBox(
                    val subtitle: String?,
                    val template: String?,
                    val title: String?
                )

                data class Rating(
                    val rating: Int?,
                    val score: Double?,
                    val volume: Int?
                )

                data class VenuePreviewItem(
                    val currency: String?,
                    val id: String?,
                    val image: Image?,
                    val name: String?,
                    val original_price: Int?,
                    val price: Int?
                ) {
                    data class Image(
                        val blurhash: String?,
                        val url: String?
                    )
                }
            }
        }

        data class Link(
            val target: String?,
            val target_sort: String?,
            val target_title: String?,
            val telemetry_object_id: String?,
            val title: String?,
            val type: String?,
            val view_name: String?
        )
    }

    data class Sorting(
        val sortables: List<Sortable>?
    ) {
        data class Sortable(
            val id: String?,
            val name: String?,
            val type: String?
        )
    }
}
