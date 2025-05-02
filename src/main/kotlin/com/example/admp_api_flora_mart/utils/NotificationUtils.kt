package com.example.admp_api_flora_mart.utils

import com.example.admp_api_flora_mart.dto.NotificationMessage
import com.example.admp_api_flora_mart.dto.UserDTO
import com.example.admp_api_flora_mart.entity.ENotificationType
import com.example.admp_api_flora_mart.entity.EOrderStatus
import java.time.LocalDateTime

class NotificationUtils {

    companion object {

        fun createOrderNotification(
            status: EOrderStatus,
            user: UserDTO,
            orderId: Long
        ): NotificationMessage {
            val (title, message) = getOrderNotificationContent(status, orderId)
            return NotificationMessage(
                type = ENotificationType.ORDER,
                title = title,
                user = user,
                message = message,
                date = LocalDateTime.now(),
                screen = "OrderDetail",
                params = mapOf("orderId" to orderId.toString())
            )
        }

        fun createPostNotification(
            user: UserDTO,
            postId: String,
            postTitle: String
        ): NotificationMessage {
            return NotificationMessage(
                type = ENotificationType.POST,
                title = "Bài viết mới",
                user = user,
                message = "Bài viết \"$postTitle\" đã được đăng.",
                date = LocalDateTime.now(),
                screen = "PostDetail",
                params = mapOf("postId" to postId)
            )
        }

        fun createEventNotification(
            user: UserDTO,
            eventId: String,
            eventName: String
        ): NotificationMessage {
            return NotificationMessage(
                type = ENotificationType.EVENT,
                title = "Sự kiện sắp diễn ra",
                user = user,
                message = "Sự kiện \"$eventName\" sắp diễn ra, đừng bỏ lỡ!",
                date = LocalDateTime.now(),
                screen = "EventDetail",
                params = mapOf("eventId" to eventId)
            )
        }

        fun createReviewNotification(
            user: UserDTO,
            reviewId: String,
            productName: String
        ): NotificationMessage {
            return NotificationMessage(
                type = ENotificationType.REVIEW,
                title = "Đánh giá mới",
                user = user,
                message = "Bạn vừa đánh giá sản phẩm \"$productName\".",
                date = LocalDateTime.now(),
                screen = "ReviewDetail",
                params = mapOf("reviewId" to reviewId)
            )
        }

        fun createCommentNotification(
            user: UserDTO,
            commentId: String,
            context: String
        ): NotificationMessage {
            return NotificationMessage(
                type = ENotificationType.COMMENT,
                title = "Bình luận mới",
                user = user,
                message = "Bạn có bình luận mới về \"$context\".",
                date = LocalDateTime.now(),
                screen = "CommentDetail",
                params = mapOf("commentId" to commentId)
            )
        }

        private fun getOrderNotificationContent(
            status: EOrderStatus,
            orderId: Long
        ): Pair<String, String> {
            return when (status) {
                EOrderStatus.NEW -> "Đơn hàng mới" to "Bạn vừa tạo đơn hàng #$orderId"
                EOrderStatus.CONFIRMED -> "Đơn hàng đã xác nhận" to "Đơn hàng #$orderId đã được xác nhận"
                EOrderStatus.PREPARING -> "Đang chuẩn bị hàng" to "Đơn hàng #$orderId đang được chuẩn bị"
                EOrderStatus.SHIPPING -> "Đang giao hàng" to "Đơn hàng #$orderId đang được vận chuyển"
                EOrderStatus.SHIPPED -> "Đã bàn giao vận chuyển" to "Đơn hàng #$orderId đã được gửi đi"
                EOrderStatus.DELIVERED -> "Giao hàng thành công" to "Bạn đã nhận đơn hàng #$orderId"
                EOrderStatus.CANCELED -> "Đơn hàng đã huỷ" to "Đơn hàng #$orderId đã bị huỷ"
            }
        }
    }
}
