package com.example.digits.main.presentations

import com.example.digits.numbers.presentation.Communication

interface NavigationCommunication: Communication.Mutable<NavigationStrategy> {
    interface Observe : Communication.Observe<NavigationStrategy>
    interface Mutate : Communication.Mutate<NavigationStrategy>
    interface Mutable: Observe, Mutate

    class Base : Communication.SingleUi<NavigationStrategy>(), Mutable
}