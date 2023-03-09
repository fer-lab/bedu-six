package cliTools

class Options(val description: String, val mode: OptionsKeyTypes = OptionsKeyTypes.NUMBER)  {

    private val options: ArrayList<Option> = ArrayList<Option>()
    private val dictionary: HashMap<Int, Char> = createDictionary()

    fun add(label: String, key: String ? = null)
    {
        options.add(Option(key = key ?: nextKey(), label = label))
    }

    fun get(tryAgain: Boolean = false, warning: String ? = null): OptionsResponse
    {
        displayOptions()

        var response = OptionsResponse(getResponse(), options)

        val displayWarning = fun(warning: String)
        {
            if (warning != "")
            {
                println("*** $warning ***")
            }
            else
            {
                println("*** Opción Inválida ***")
            }
        }

        if (!response.isValid() && tryAgain)
        {
            for (i in generateSequence(0) { it }) {

                displayWarning(warning.orEmpty())

                response = OptionsResponse(getResponse(), options)
                if (response.isValid())
                {
                    return response
                }
            }
        }
        else if (!response.isValid())
        {
            displayWarning(warning.orEmpty())
        }

        return response
    }

    private fun displayOptions() {
        var body = ""

        if (options.size > 0)
        {
            for (option in options)
            {
                body += "${option.key}. ${option.label}\n"
            }
        }
        else
        {
            body = "No existen opciones"
        }

        Banner.display(this.description, body.trim())
    }

    private fun getResponse(): String
    {
        print(": ")
        return readln().lowercase().trim()
    }


    fun nextKey(): String
    {
        val nextItem: Int = (options.size + 1)

        return if (isModeNumber()) {
            nextItem.toString()
        } else {

            if (dictionary.containsKey(nextItem)) {
                dictionary[nextItem].toString()
            } else {
                "??"
            }

        }
    }


    private fun isModeNumber(): Boolean {
        return !isModeLetter()
    }

    private fun isModeLetter(): Boolean {

        return when (mode) {
            OptionsKeyTypes.LETTER -> true
            else -> false
        }
    }

    private fun createDictionary(): HashMap<Int, Char>
    {
        var value: Char
        var key: Int
        val map = HashMap<Int, Char>()

        value = 'a'
        key = 1

        while (value <= 'z') {
            map[key] = value
            key++
            ++value
        }

        return map
    }
}