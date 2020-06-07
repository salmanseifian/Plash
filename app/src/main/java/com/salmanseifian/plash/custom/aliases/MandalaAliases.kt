package com.salmanseifian.plash.custom.aliases

import com.salmanseifian.plash.base.Result
import com.salmanseifian.plash.room.models.DbDateFact
import com.salmanseifian.plash.room.models.Note


typealias ListOfDateFacts = List<DbDateFact>

typealias DateFactResult = Result<ListOfDateFacts>

typealias ListOfNotes = List<Note>

typealias NoteResult = Result<ListOfNotes>