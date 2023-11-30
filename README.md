# Object-Oriented-Programming Project
# Video demo
# Giới thiệu ứng dụng học tiếng Anh EDUET
![Video giới thiệu](https://www.youtube.com/watch?v=TiC7zKhceZA)
## Đăng ký
Vẫn như các ứng dụng thông thường khác, chúng tôi cần một số thông tin cơ bản của người dùng để thực hiện các thao tác quản lý cơ sở. Bạn cần nhập đúng gmail để khi bấm vào nút “Xác nhận gmail”, mã OTP sẽ được tự động gửi chính xác tới địa chỉ gmail của bạn.
	
## Đăng nhập
Bạn có thể đăng nhập từ nhiều máy tính khác nhau bởi chúng tôi đã đẩy dữ liệu của bạn lên cloud. Mỗi người dùng được đặt với bộ dữ liệu riêng biệt, dễ dàng quản lý. Mật khẩu của bạn đã được chúng tôi hash lại với mã MD5 để đảm bảo tính bảo mật với thông tin cá nhân của người dùng.

## Home
	
Khi đăng nhập thành công, màn hình sẽ chuyển tới màn hình “Home”. Chú cừu ở đây được coi như là linh vật của EDUET, được thiết lập để tạo từ mới mỗi ngày cho người dùng, gây hứng thú cũng như tăng khả năng ghi nhớ các từ vựng. 
Để giúp người dùng có trải nghiệm tốt nhất, gần gũi và hiểu hơn về EDUET, chúng tôi đã tạo ra một chatbot tương tác với người dùng ở phía bên phải màn hình. Bạn có thể hỏi chatbot về thông tin, các chức năng trong ứng dụng; dữ liệu cá nhân người dùng; các phương pháp học tiếng Anh hiệu quả….
	
## Search
	
	Đây là phần giúp tìm hiểu thông tin cơ bản của 1 từ vựng tiếng Anh. Thanh tìm kiếm bên trái dùng để tìm kiếm từ, thông tin tương ứng sẽ xuất hiện ở phần văn bản bên phải với ý nghĩa của từ theo từng từ loại (danh, tính, động, trạng…) và cách phiên âm. 
Với thanh công cụ bên phải, bạn sẽ nhận được một số tính năng cơ bản sau:
+ “Speak” có chức năng phát âm từ
+ “Edit” giúp điều chỉnh nghĩa của từ
+ “Delete” tức là xóa từ
+ Kí hiệu ngôi sao tức là đánh dấu từ
+ “Add” thêm một từ mới vào
+ “Syms”: bấm vào đây, màn hình sẽ hiện lên các thẻ gồm các từ đồng nghĩa và ý nghĩa tương ứng của chúng
+ “Anms”: bấm vào đây, màn hình sẽ hiện lên các thẻ gồm các từ trái nghĩa và ý nghĩa tương ứng của chúng

## Learn
Như đã nói ở phần trước, “Search” có chức năng đánh dấu từ, và những từ được đánh dấu đó sẽ được đưa về đây, trong “Favorite Words”. Mỗi người sẽ có 1 bảng favorite words riêng và được gửi lên cloud nên mọi hành động sẽ được cập nhật theo thời gian thực. Trong favorite words gồm các thẻ của các từ bạn đã đánh dấu, bạn cũng có thể xóa chúng với “Xóa”.

Phần “Search” còn chức năng khác, đó là xóa từ, vậy những từ đấy sẽ đi đâu? Chúng được lưu vào bảng “Deleted Words” của “Learn”. Tương tự với favorite, bạn có thể xóa hoàn toàn từ bạn đã xóa trước đó khi click “Xóa”.

Với phương châm lấy người dùng làm trung tâm, đảm bảo kích thích khả năng tiếng Anh của người dùng, chúng tôi có đặt vào “Learn” các bài kiểm tra tiếng Anh. Rất xin lỗi vì tiềm lực có hạn, chúng tôi bước đầu mới tạo được các bài kiểm tra listening ielts cơ bản.


## API
	API - Thực ra chúng tôi nên đặt nó là tiện ích mới đúng, rất mong bạn thông cảm. Đây là một phần mang rất nhiều tiện ích thiết thực dành cho người học tiếng Anh. Đầu tiên chúng ta có 2 phần văn bản: 1 để ghi, 1 để hiện kết quả. 
	Chúng ta có nút bấm “MICRO” với chức năng nhận diện giọng nói và chuyển chúng thành văn bản, nút bấm “VOLUME” với chức năng chuyển văn bản thành giọng nói.
	Với nút “Bàn tay gấu”, khi bấm vào bạn có thể thực hiện 1 trong 4 chức sau tùy theo lựa chọn:
+ Translate: Công cụ dịch các đoạn văn bản dài, phức tạp từ tiếng Anh sang tiếng Việt và ngược lại
+ Check: Công cụ phát hiện lỗi sai ngữ pháp và chính tả, đề xuất các chỉnh sửa thích hợp cho đoạn văn tiếng Anh của bạn
+ Rewrite: Công cụ viết lại câu với 3 phương án: theo cách tối giản, theo cách không trang trọng và theo cách trang trọng
+ ChatAI: Công cụ tương tác với trí tuệ nhân tạo Chat GPT, giúp giải đáp những thắc mắc trong học tập và công việc
	
## Game
	Học - Hành - Chơi. Chúng tôi quyết định tạo ra những trò chơi phong phú giàu tính tư duy với mong muốn cải thiện khả năng tiếng Anh của người dùng nhất. Bởi vì nguồn lực có hạn, chúng tôi đã tạo ra 2 game cơ bản như sau:
### Wormd: 
Ăn từ theo đúng thứ tự chữ cái (dạng game snake).

### Hangman: 
Đoán từ theo từng ký tự.
	
# Lời chào
