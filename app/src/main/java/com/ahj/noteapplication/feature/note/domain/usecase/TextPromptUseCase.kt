package com.ahj.noteapplication.feature.note.domain.usecase

import com.ahj.noteapplication.feature.note.domain.model.TextActions
import com.ahj.noteapplication.feature.note.domain.repository.RepositoryNote
import javax.inject.Inject


class TextPromptUseCase @Inject constructor(
    private val repository: RepositoryNote
) {

    suspend operator fun invoke(text: String, actions: TextActions): Result<String> {

        val prompt = when(actions){
            TextActions.SHORT -> "سلام لطفا این متن رو کوتاه کن به شکلی که خوانا و مرتب باشد: $text"
            TextActions.LONG -> "سلام لطفا این متن رو برام گسترش بده به شکلی که خوانا و مرتب باشد: $text"
            TextActions.TRANSLATE -> "سلام لطفا این متن رو برام ترجمه کن به زبان فارسی به شکلی که خوانا و مرتب باشد و توضیحاتش بهم نریزه: $text"
            TextActions.PROCESS_MATH -> "سلام لطفا این معادله یا محاسبه رو برام انجام بده به شکلی که قابل فهم باشه و با زبان ساده به من هر بخشش رو توضیح بده و در نهایت بعد از توضیحاتت جواب کامل رو مجدد بزار: $text"
        }

        return repository.textPrompt(prompt)
    }


}